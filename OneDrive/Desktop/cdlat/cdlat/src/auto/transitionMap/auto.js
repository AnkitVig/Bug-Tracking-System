'use strict';

import Lang from '../../utils/lang.js';
import Array from '../../utils/array.js';
import Dom from '../../utils/dom.js';
import Tooltip from '../../ui/tooltip.js';
import Grid from '../gridLayer/grid.js';
import Automated from '../automated.js';
import State from '../../simulation/state.js';
import Frame from '../../simulation/frame.js';
import Palette from '../../simulation/palettes/d3.js';

export default Lang.Templatable("Auto.TransitionMap", class AutoTransitionMap extends Automated { 

	constructor(config, simulation) {
		super(new Grid(), simulation);
		
		this.z = config.z;
		this.min = config.min;
		this.max = config.max;
		this.n = config.n;
		
		var h1 = this.Widget.On("MouseMove", this.onMouseMove_Handler.bind(this));
		var h2 = this.Widget.On("MouseOut", this.onMouseOut_Handler.bind(this));
		var h3 = this.Simulation.On("Move", this.onSimulationMove_Handler.bind(this));
		var h4 = this.Simulation.On("Jump", this.onSimulationJump_Handler.bind(this));
		
		this.Handle([h1, h2, h3, h4]);
		
		this.BuildTooltip();
		
		this.palette = new Palette("internal");
		this.state = this.GetState(simulation, simulation.state.i);
		
		var max = this.GetMaxTransitions(simulation);
		
		var classes = this.palette.Buckets(this.n, this.min, this.max, 0, max);
		
		this.Widget.Node("title").innerHTML = Lang.Nls("TransitionMap_Title", [this.z + 1]);
	}
	
	GetState(simulation, i) {
		var state = State.Zero(simulation.models);
		
		for (var j = 0; j < i; j++) {
			Array.ForEach(simulation.frames[j].transitions, function(t) {
				state.model[t.id]++;				
			});
		}
				
		return state;
	}
	
	GetMaxTransitions(simulation) {
		var state = this.GetState(simulation, simulation.frames.length - 1);
		
		var max = 0;
		
		for(var id in state.model){
			var idx = id.split("-");
			var i = idx[0] + "-" + idx[1] + "-" + this.z;
			var v = state.model[id];
				
			if (v > max) max = v;

		}
		return max;
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
		this.Widget.Draw(this.state, this.z, this.palette, this.Simulation.selection);
	}

	onSimulationMove_Handler(ev) {
		var plus = ev.direction == "next" ? 1 : -1; 
		var frame = new Frame("internal", "0");
	
		Array.ForEach(ev.frame.transitions, function(t) {			

			this.state.model[t.id] +=plus;

			frame.AddTransition(t.id, this.state.model[t.id], null);
		
		}.bind(this));
		
		this.Widget.DrawChanges(frame, this.z, this.palette, this.Simulation.selection);
	}
	
	onSimulationJump_Handler(ev) {		
		this.state = this.GetState(this.Simulation, ev.state.i);
		
		this.Widget.Draw(this.state, this.z, this.palette, this.Simulation.selection);
	}
	
	onMouseMove_Handler(ev) {
		//var state = this.state.GetValue(ev.data.x, ev.data.y, this.z);
		var id = ev.data.x + "-" + ev.data.y + "-" + this.z;
		var state = this.state.model[id];

		var subs = [ev.data.x, ev.data.y, this.z, state];
		
		this.tooltip.nodes.label.innerHTML = Lang.Nls("Widget_AutoTransitionMap_Tooltip_Title", subs);
	
		this.tooltip.Show(ev.x + 20, ev.y);
	}
	
	onMouseOut_Handler(ev) {
		this.tooltip.Hide();
	}
	
	Save() {
		return { name:"Auto.TransitionMap", config:{ z:this.z, min:this.min, max:this.max , n:this.n }}
	}
});