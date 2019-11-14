'use strict';

import Sim from '../../utils/sim.js';
import Lang from '../../utils/lang.js';
import Array from '../../utils/array.js';
import Dom from '../../utils/dom.js';
import Playback from '../../widgets/playback.js';
import Widget from '../../ui/widget.js';
import svg from '../../widgets/svg.js';
	
export default Lang.Templatable("Config.DevsDiagram", class ConfigDevsDiagram extends Widget { 

	constructor() {
		super();
		
		this.Node("continue").addEventListener("click", this.onLoadClick_Handler.bind(this));
		
		this.svg = "";

		var name = Lang.Nls("Widget_AutoDevsDiagram");

		this.Node("title").innerHTML = Lang.Nls("Configurator_Title", [name]);
		this.Node("svg").On("Change", this.onsvgChange_Handler.bind(this));
	}

	onsvgChange_Handler(ev) {

		var file = this.Node("svg").files[0];	
	
		var p = Sim.ReadFile(file, (d) => { return d; });
		
		p.then(file => { 
			this.svg = file.result;
		});
	}
	
	
	onError_Handler(ev) {
		// TODO : Probably handle error here, alert message or something.
		this.Node("svg").Reset();
		
		alert(ev.error.toString())
	}
	onLoadClick_Handler(ev) {	
		var configuration={
			svg : this.svg
		};

		this.Emit("Configured", { configuration:configuration });
	}
	
	Save() {
		return { name:"Config.DevsDiagram" };
	}
	
	Template() {
		return "<div class='configuration'>" + 
				   "<div handle='title' class='configuration-title'></div>" + 
				   "<div class='configuration-line'>" + 
					  "<div class='label'>nls(Widget_DevsDiagram)</div>" +
					    "<div handle='svg' class='svg' widget='Widget.svg'></div>" +
					 
				   "</div>" +
				  " <div handle='content'></div>"+
				   "<button handle='continue' class='load'>nls(Configurator_Continue)</button>" + 
			   "</div>";
	}
});