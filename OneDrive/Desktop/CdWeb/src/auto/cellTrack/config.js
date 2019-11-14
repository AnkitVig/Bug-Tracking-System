'use strict';

import Lang from '../../utils/lang.js';
import Array from '../../utils/array.js';
import Dom from '../../utils/dom.js';
import Widget from '../../ui/widget.js';

export default Lang.Templatable("Config.CellTrack", class ConfigCellTrack extends Widget { 

	constructor() {
		super();
		
		this.Node("continue").addEventListener("click", this.onLoadClick_Handler.bind(this));
		
		var name = Lang.Nls("CellTrackChart_Title");
		
		this.Node("title").innerHTML = Lang.Nls("Configurator_Title", [name]);
	}
		
	onLoadClick_Handler(ev) {		
		var configuration = {
			type : this.Node("type").value
		}
		
		this.Emit("Configured", { configuration:configuration });
	}
	
	Save() {
		return { name:"Config.CellTrack" };
	}
	
	Template() {
		return "<div class='configuration'>" + 
				   "<div handle='title' class='configuration-title'></div>" + 
				   "<div class='configuration-line'>" + 
					  "<div class='label'>nls(CellTrackConfig_Type)</div>" +
					  "<select handle='type' class='value'>" + 
						  "<option value='continuous'>nls(CellTrackConfig_Type_Discrete)</option>" + 
						  "<option value='discrete'>nls(CellTrackConfig_Type_Continuous)</option>" + 
					  "</select>" +
				   "</div>" +
				   "<button handle='continue' class='load'>nls(Configurator_Continue)</button>" +
			   "</div>";
	}
});