'use strict';

import Lang from '../../utils/lang.js';
import Array from '../../utils/array.js';
import Dom from '../../utils/dom.js';
import Tooltip from '../../ui/tooltip.js';
import Grid from './grid.js';
import Automated from '../automated.js';

export default Lang.Templatable("Auto.Grid", class AutoGrid extends Automated { 

	constructor(config, simulation) {
		super(new Grid(), simulation);
		
		this.z = config.z;
		
		var h1 = this.Widget.On("MouseMove", this.onMouseMove_Handler.bind(this));
		var h2 = this.Widget.On("MouseOut", this.onMouseOut_Handler.bind(this));
		var h3 = this.Widget.On("Click", this.onClick_Handler.bind(this));
		var h4 = this.Simulation.On("Move", this.onSimulationMove_Handler.bind(this));
		var h5 = this.Simulation.On("Jump", this.onSimulationJump_Handler.bind(this));
		var h6 = this.Simulation.palette.On("Change", this.onSimulationPaletteChanged_Handler.bind(this));
		
		this.Handle([h1, h2, h3, h4, h5, h6]);
		
		this.BuildTooltip();
	}
	
	Destroy() {
		super.Destroy();
	}
	
	BuildTooltip() {
		this.tooltip = new Tooltip(document.body);
		
		this.tooltip.nodes.label = Dom.Create("div", { className:"tooltip-label" }, this.tooltip.Node("content"));
	}
	
	Refresh() {
		this.Resize();
		this.Draw();
	}
	
	Resize() {
		this.Widget.Resize(this.Simulation.Size);
	}
	
	Draw() {
		var s = this.Simulation;
		
		this.Widget.Draw(s.state, this.z, s.Palette, s.Selection);
	}

	onSimulationMove_Handler(ev) {	
		var s = this.Simulation;
		
		this.Widget.DrawChanges(ev.frame, this.z, s.Palette, s.Selection);
	}
	
	onSimulationJump_Handler(ev) {
		var s = this.Simulation;
		
		this.Widget.Draw(s.state, this.z, s.Palette, s.Selection);
	}
	
	onSimulationPaletteChanged_Handler(ev) {
		var s = this.Simulation;
		
		this.Widget.Draw(s.state, this.z, s.Palette, s.Selection);
	}
	
	onMouseMove_Handler(ev) {
		var id = ev.data.x + "-" + ev.data.y + "-" + this.z;
		var state = this.simulation.state.model[id];
		var subs = [ev.data.x, ev.data.y, this.z, state];
		
		this.tooltip.nodes.label.innerHTML = Lang.Nls("Widget_AutoGrid_Tooltip_Title", subs);
	
		this.tooltip.Show(ev.x + 20, ev.y);
	}
	
	onMouseOut_Handler(ev) {
		this.tooltip.Hide();
	}
	
	onClick_Handler(ev) {
		var id = ev.data.x + "-" + ev.data.y + "-" + this.z;
		var isSelected = this.Simulation.Selection.IsSelected(id);		
		
		if (!isSelected) {
			this.Simulation.Selection.Select(id);
			
			var color = this.Simulation.Palette.SelectedColor;
		} 
		
		else {
			this.Simulation.Selection.Deselect(id);

			var v = this.simulation.state.model[id];
			
			var color = this.Simulation.Palette.GetColor(v);
		}
		
		this.Widget.DrawCellBorder(ev.data.x, ev.data.y, color);
	}
	
	Save() {
		return { name:"Auto.Grid", config:{ z:this.z }}
	}
});