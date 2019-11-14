'use strict';

import Lang from '../../utils/lang.js';
import Array from '../../utils/array.js';
import Dom from '../../utils/dom.js';
import Diagram from './diagram.js';
import Automated from '../automated.js';

import Tooltip from '../../ui/tooltip.js';

export default Lang.Templatable("Auto.DevsDiagram", class AutoDevsDiagram extends Automated { 

	constructor(config, simulation) {
		super(new Diagram(config.svg), simulation);
		this.svg=config.svg;
		this.selected = [];
		//this.type = 'continuous';
		var h1 = this.Widget.On("MouseMove", this.onMouseMove_Handler.bind(this));
		var h2 = this.Widget.On("MouseOut", this.onMouseOut_Handler.bind(this));
		var h3 = this.Widget.On("Click", this.onClick_Handler.bind(this));
		var h4 = this.Simulation.On("Move", this.onSimulationMove_Handler.bind(this));
		var h5 = this.Simulation.On("Jump", this.onSimulationMove_Handler.bind(this));
		var h6 = this.Simulation.Selection.On("Change", this.onSelectionChange_Handler.bind(this));
		
		
		this.Handle([h1, h2, h3, h4, h5,h6]);
		
		this.UpdateSelected();
		this.Data();
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
		
		this.Widget.Draw(s.state);
	}

	Data(selected, frames) {
		var data = {
			type : this.type,
			series : Array.Map(this.selected, function(id) { return { id:id, values:[] }; }),
			times : [],
			transitions : [],
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
			data.transitions.push(f.transitions);
		}.bind(this));
		
		this.Widget.Data(data);
	
	}

	onSimulationMove_Handler(ev) {
	var s = this.Simulation;
	
		this.Widget.DrawChanges(s.state);
	}
	
	onSelectionChange_Handler(ev) {
	
		var s = this.Simulation;
		//this.Widget.DrawChanges(s.state,s.frames,this.selected);  //both works same, no event at this selection
		//this.Widget.DrawChanges(s.state,ev.frame,this.selected);
			this.Widget.DrawChanges(s.state);
	}
	
	UpdateSelected() {
		this.selected = Array.Map(this.Simulation.Selection.Selected, function(s) {
		//	console.log(s);
			return s;
		});
	}
	

	
	onMouseMove_Handler(ev) {
	var s = this.Simulation;
		var subs = [ev.data2,s.state.model[ev.data2]];
		
		this.tooltip.nodes.label.innerHTML = Lang.Nls("Widget_DEVS_Tooltip_Title", subs);
	
		this.tooltip.Show(ev.x + 20, ev.y);
	}
	

	onClick_Handler(ev) {
	
		var id = [ev.selectedid];
		
		//var isSelected = this.Simulation.Selection.IsSelected(id);
		//console.log(isSelected);
		//if (!isSelected) {
			var fillstroke = 'red';
			this.Widget.DrawSVGBorder(id,fillstroke);
		//} 
		

		
		
	
	}

	onMouseOut_Handler(ev) {
		this.tooltip.Hide();
	}
	
	

	Save() {
		return { name:"Auto.DevsDiagram", config:{ svg:this.svg }}
	}


});