'use strict';

import Lang from '../../utils/lang.js';
import Array from '../../utils/array.js';
import Dom from '../../utils/dom.js';
import Widget from '../../ui/widget.js';

export default Lang.Templatable("Config.TransitionMap", class ConfigTransitionMap extends Widget { 

	constructor() {
		super();
		
		this.Node("continue").addEventListener("click", this.onLoadClick_Handler.bind(this));
		
		this.Node("z").value = 0;
		this.Node("n").value = 8;
		this.Node("min").value = "#fbdaae";
		this.Node("max").value = "#cd5607";
		
		var name = Lang.Nls("Widget_AutoTransitionMap");
		
		this.Node("title").innerHTML = Lang.Nls("Configurator_Title", [name]);
	}
		
	onLoadClick_Handler(ev) {		
		var configuration = {
			z : +this.Node("z").value,
			min : this.Node("min").value,
			max : this.Node("max").value,
			n : this.Node("n").value,
			colors : ["#ffffcc","#ffeda0","#fed976","#feb24c","#fd8d3c","#fc4e2a","#e31a1c","#bd0026","#800026"]
		}
		
		this.Emit("Configured", { configuration:configuration });
	}
	
	Save() {
		return { name:"Config.Grid" };
	}
	
	Template() {
		return "<div class='configuration transitionMap'>" + 
				   "<div handle='title' class='configuration-title'></div>" + 
				   "<div class='configuration-line'>" + 
					  "<div class='label'>nls(TransitionMapConfig_Z)</div>" +
					  "<input handle='z' class='value' type='number'></input>" +
				   "</div>" +
				   "<div class='configuration-line'>" + 
					  "<div class='label'>nls(TransitionMapConfig_N)</div>" +
					  "<input handle='n' class='value' type='number'></input>" +
				   "</div>" +
				   "<div class='label colors'>nls(TransitionMapConfig_ColorRange)</div>" +
				   "<div class='configuration-line colors'>" + 
					  "<div class='label'>nls(TransitionMapConfig_ColorMin)</div>" +
					  "<input handle='min' class='value' type='color'></input>" +
					  "<div class='label'>nls(TransitionMapConfig_ColorMax)</div>" +
					  "<input handle='max' class='value' type='color'></input>" +
				   "</div>" +
				   "<button handle='continue' class='load'>nls(Configurator_Continue)</button>" + 
			   "</div>";
	}
});