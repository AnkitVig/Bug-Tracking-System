'use strict';

import Lang from '../../utils/lang.js';
import Array from '../../utils/array.js';
import Dom from '../../utils/dom.js';
import Tooltip from '../../ui/tooltip.js';
import CellTrackChart from './chart.js';
import Automated from '../automated.js';

export default Lang.Templatable("Auto.CellTrackChart", class AutoCellTrackChart extends Automated { 
	
	constructor(config, simulation) {
	
		super(new CellTrackChart(), simulation);
		
		this.selected = [];
		this.type = config.type;
		
		var h1 = this.Widget.On("MouseMove", this.onMouseMove_Handler.bind(this));
		var h2 = this.Widget.On("MouseOut", this.onMouseOut_Handler.bind(this));
		var h3 = this.Simulation.On("Move", this.onSimulationMove_Handler.bind(this));
		var h4 = this.Simulation.On("Jump", this.onSimulationMove_Handler.bind(this));
		var h5 = this.Simulation.Selection.On("Change", this.onSelectionChange_Handler.bind(this));
		
		this.Handle([h1, h2, h3, h4, h5]);
		
		this.UpdateSelected();
				
		this.BuildTooltip();
		
		this.Data();
	}
	
	Destroy() {
		super.Destroy();
		
		Dom.Remove(document.body, this.tooltip.Node("root"));
		
		this.selected = null;
		this.tooltip = null;
	}
	
	BuildTooltip() {
		this.tooltip = new Tooltip(document.body);
		
		this.tooltip.Empty();
		
		this.tooltip.nodes.title = Dom.Create("div", { className:"tooltip-title" }, this.tooltip.Node("content"));
		
		this.tooltip.nodes.lines = Array.Map(this.selected, function(id, i) {
			var div = Dom.Create("div", { className:"tooltip-line" }, this.tooltip.Node("content"));
			var svg = Dom.CreateSVG("svg", { class:`legend` }, div);
			var lgd = Dom.CreateSVG("rect", { class:`mouse line-${i + 1}` }, svg);

			return Dom.Create("div", { className:"tooltip-label" }, div);
		}.bind(this));
	}
	
	Refresh() {
		this.Resize();
		this.Draw();
		this.Update();
	}
	
	Resize() {
		this.Widget.Resize();
	}
	
	Draw() {
		this.Widget.Draw();
	}
	
	Update() {
		this.Widget.Update(this.Simulation.state.i);
	}
	
	Data(selected, frames) {
		var data = {
			type : this.type,
			series : Array.Map(this.selected, function(id) { return { id:id, values:[] }; }),
			times : [],
			max: -Infinity,
			min: Infinity 
		};
				
		Array.ForEach(this.Simulation.frames, function(f, i) {
			Array.ForEach(this.selected, function(id, j) {
				var t = f.TransitionById(id);
				var v = (t) ? t.Value : data.series[j].values[i - 1];
				
				data.series[j].values.push(v)
				
				if (data.min > v) data.min = v; 
				if (data.max < v) data.max = v; 
			}.bind(this));
								
			data.times.push(f.time);
		}.bind(this));
		
		this.Widget.Data(data);
		//console.log(data);
	}

	// TODO: This can be made more efficient by applying only the transitions
	onSimulationMove_Handler(ev) {
		this.Update();
	}
	
	onMouseMove_Handler(ev) {
		this.tooltip.nodes.title.innerHTML = Lang.Nls("CellTrackChart_Tooltip_Title", [ev.data.x]);
		
		for (var i = 0; i < ev.data.y.length; i++) {
			var d = ev.data.y[i];
			
			this.tooltip.nodes.lines[i].innerHTML = Lang.Nls("CellTrackChart_Tooltip_Line", [d.id, d.y]);
		}
		
		this.tooltip.Show(ev.x + 20, ev.y);
	}
	
	onMouseOut_Handler(ev) {
		this.tooltip.Hide();
	}
	
	onSelectionChange_Handler(ev) {
		this.UpdateSelected();
		this.BuildTooltip();
		this.Data();
		this.Refresh();
	}
	
	UpdateSelected() {
		this.selected = Array.Map(this.Simulation.Selection.Selected, function(s) {
			return s;
		});
	}
	
	Save() {
		return { name:"Auto.CellTrackChart", config: { type:this.type }};
	}
});