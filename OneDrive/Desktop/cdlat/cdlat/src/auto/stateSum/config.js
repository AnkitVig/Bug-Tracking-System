'use strict';

import Lang from '../../utils/lang.js';
import Array from '../../utils/array.js';
import Dom from '../../utils/dom.js';
import Widget from '../../ui/widget.js';

export default Lang.Templatable("Config.StateSum", class ConfigCellTrack extends Widget { 

	constructor() {
		super();
		
		this.Node("continue").addEventListener("click", this.onLoadClick_Handler.bind(this));
		
		var name = Lang.Nls("StateChart_Title");
		
		this.Node("title").innerHTML = Lang.Nls("Configurator_Title", [name]);
	}
		
	onLoadClick_Handler(ev) {		
		var configuration = {
			z : [+this.Node("z").value],
			tracked : this.Node("tracked").value
		}
		
		this.Emit("Configured", { configuration:configuration });
	}
	
	Save() {
		return { name:"Config.StateSum" };
	}
	
	Template() {
		return "<div class='configuration'>" + 
				   "<div handle='title' class='configuration-title'></div>" + 
				   "<div class='configuration-line'>" + 
					  "<div class='label'>nls(StateChart_Title)</div>" +
					  "<input handle='z' class='value' type='number' value='0'></input>" +
				   "</div>" +
				   "<div class='configuration-line'>" + 
					  "<div class='label'>nls(CellTrackConfig_Tracked)</div>" +
					  "<input handle='tracked' class='value' type='text' value='100;101;102'></input>" +
					  // "<div handle='tracked' class='value' value='1;2;3;4;5'></div>" +
				   "</div>" +
				   "<button handle='continue' class='load'>nls(Configurator_Continue)</button>" + 
			   "</div>";
	}
});