'use strict';

import Array from '../utils/array.js';
import Lang from '../utils/lang.js';
import Dom from '../utils/dom.js';
import Sim from '../utils/sim.js';
import Widget from '../ui/widget.js';
import Templated from '../components/templated.js';

export default Lang.Templatable("Widget.Palette", class Palette extends Widget { 

	constructor(id) {
		super(id);
		
		this.Node("add").addEventListener("click", this.onAddClick_Handler.bind(this));
	}
	
	Template() {
		return	"<div class='palette'>" + 
					"<div handle='content' class='palette-classes'>" +
						"<div class='palette-row palette-row-header'>" +
							"<div class='palette-start'>nls(Palette_Start)</div>" +
							"<div class='palette-end'>nls(Palette_End)</div>" +
						"</div>" +
					"</div>" +
					"<button handle='add' class='palette-add'>nls(Palette_Add)</button>" +
				"</div>";
	}
	
	Initialize(simulation) {
		this.simulation = simulation;
		
		this.Reset();
		
		this.simulation.Palette.On("Session", this.onPaletteSession_Handler.bind(this));
	}
	
	Reset() {
		Dom.Empty(this.Node("content"));
				
		Array.ForEach(this.simulation.Palette.classes, function(c) {
			this.AddClass(c);
		}.bind(this));
	}
	
	AddClass(clss) {
		var template = this.RowTemplate(clss.start, clss.end, Sim.RgbToHex(clss.color));
		var row = new Templated(this.Node("content"), { template:template });
		
		row.Node("remove").addEventListener("click", this.onClassRemove_Handler.bind(this, row, clss));
		
		row.Node("start").addEventListener("change", this.onStartChange_Handler.bind(this, row, clss));
		row.Node("end").addEventListener("change", this.onEndChange_Handler.bind(this, row, clss));
		row.Node("color").addEventListener("change", this.onColorChange_Handler.bind(this, row, clss));
	}
	
	onPaletteSession_Handler() {
		this.Reset();
	}
	
	onClassRemove_Handler(row, clss, ev) {
		this.simulation.Palette.RemoveClass(clss);
		
		Dom.Remove(row.Node("root"), this.Node("content"));
	}
	
	onColorChange_Handler(row, clss, ev) {		
		this.simulation.Palette.SetColor(clss, Sim.HexToRgb(ev.target.value));
	}
	
	onStartChange_Handler(row, clss, ev) {		
		this.simulation.Palette.SetStart(clss, +ev.target.value);
	}
	
	onEndChange_Handler(row, clss, ev) {		
		this.simulation.Palette.SetEnd(clss, +ev.target.value);
	}
	
	onAddClick_Handler(ev) {
		var clss = this.simulation.Palette.AddClass(0, 0, [0,0,0]);
		
		this.AddClass(clss);
	}
	
	RowTemplate(start, end, color) {
		var t = "<div handle='root' class='palette-row'>" + 
				   "<input handle='start' class='palette-start' type='number' value='{0}'></input>" +
				   "<input handle='end' class='palette-end' type='number' value='{1}'></input>" +
				   "<input handle='color' class='palette-color' type='color' value='{2}'></input>" +
				   "<button handle='remove' class='remove'>×</button>" +
			    "</div>";
				
		return Lang.Format(t, [start, end, color]);
	}
});