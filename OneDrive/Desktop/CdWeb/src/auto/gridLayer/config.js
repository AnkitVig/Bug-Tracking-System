'use strict';

import Lang from '../../utils/lang.js';
import Array from '../../utils/array.js';
import Dom from '../../utils/dom.js';
import Widget from '../../ui/widget.js';

export default Lang.Templatable("Config.Grid", class ConfigGrid extends Widget { 

	constructor() {
		super();
		
		this.Node("continue").addEventListener("click", this.onLoadClick_Handler.bind(this));
		
		this.Node("z").value = 0;
		
		var name = Lang.Nls("Widget_AutoGrid");
		
		this.Node("title").innerHTML = Lang.Nls("Configurator_Title", [name]);
	}
		
	onLoadClick_Handler(ev) {		
		var configuration = {
			z : +this.Node("z").value
		}
		
		this.Emit("Configured", { configuration:configuration });
	}
	
	Save() {
		return { name:"Config.Grid" };
	}
	
	Template() {
		return "<div class='configuration'>" + 
				   "<div handle='title' class='configuration-title'></div>" + 
				   "<div class='configuration-line'>" + 
					  "<div class='label'>nls(Widget_ConfigGrid)</div>" +
					  "<input handle='z' class='value' type='number'></input>" +
				   "</div>" +
				   "<button handle='continue' class='load'>nls(Configurator_Continue)</button>" + 
			   "</div>";
	}
});