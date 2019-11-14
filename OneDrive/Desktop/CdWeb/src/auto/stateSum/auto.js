'use strict';

import Lang from '../../utils/lang.js';
import Array from '../../utils/array.js';
import Dom from '../../utils/dom.js';
import Tooltip from '../../ui/tooltip.js';
import StateChart from './chart.js';
import Automated from '../automated.js';

export default Lang.Templatable("Auto.StateChart", class AutoStateChart extends Automated { 

	constructor(config, simulation) {
		super(new StateChart(), simulation);
		
		this.temp = config.tracked;
		
		this.z = config.z;
		this.tracked = this.GetTemporaryTracked(config.tracked, simulation.Palette);
		this.yMax = simulation.StateMaxFrequency;
		
		var h1 = this.Widget.On("MouseMove", this.onMouseMove_Handler.bind(this));
		var h2 = this.Widget.On("MouseOut", this.onMouseOut_Handler.bind(this));
		var h3 = this.Simulation.On("Move", this.onSimulationMove_Handler.bind(this));
		var h4 = this.Simulation.On("Jump", this.onSimulationMove_Handler.bind(this));
		var h5 = this.Simulation.palette.On("Change", this.onSimulationPaletteChange_Handler.bind(this));
		
		this.Handle([h1, h2, h3, h4, h5]);
		
		this.BuildTooltip();
		
		this.Data();
	}
	
	Destroy() {
		super.Destroy();
	}
	
	GetTemporaryTracked(stracked, palette) {		
		return Array.Map(stracked.split(";"), function(s) { 			
			return { label:s, value:+s, color:palette.GetColor(+s) }
		});
	}
	
	BuildTooltip() {
		this.tooltip = new Tooltip(document.body);
		
		this.tooltip.nodes.label = Dom.Create("div", { className:"tooltip-label" }, this.tooltip.Node("content"));
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
		this.Widget.Draw(this.yMax);
	}
	
	Update() {
		this.Widget.Update();
	}
	
	Data() {		
		var state = this.Simulation.state;
	
		Array.ForEach(this.tracked, function(t) {
			t.total = 0;
			for(var id in state.model){
				var value = state.model[id];
												
				if (t.value == value) t.total++;
				
				if (!t.range) continue;
				
				if (t.range.min < value && t.range.max >= value) t.total++;
			}

		}.bind(this));
		
		this.Widget.Data(this.tracked);
	}
	
	// TODO: This can be made more efficient by applying only the transitions
	onSimulationMove_Handler(ev) {
		this.Data();
		this.Update();
	}
	
	onSimulationPaletteChange_Handler(ev) {			
		this.tracked = this.GetTemporaryTracked(this.temp, this.Simulation.Palette);
		
		this.Data();
		this.Draw(this.yMax);
	}
	
	onMouseMove_Handler(ev) {
		var subs = [this.Simulation.state.i, ev.data.total, ev.data.label];
		
		this.tooltip.nodes.label.innerHTML = Lang.Nls("StateChart_Tooltip_Title", subs);
	
		this.tooltip.Show(ev.x + 20, ev.y);
	}
	
	onMouseOut_Handler(ev) {
		this.tooltip.Hide();
	}
	
	Save() {
		return { name:"Auto.StateChart", config: { z:this.z, tracked:this.temp }}
	}
});
