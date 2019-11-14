'use strict';

import Lang from '../utils/lang.js';
import Dom from '../utils/dom.js';
import Widget from '../ui/widget.js';

export default Lang.Templatable("Widget.Info", class Info extends Widget { 

	constructor(id) {
		super(id);
	}
	
	Template() {
		return	"<div class='info-title'>nls(Info_Title)</div>" +
				"<div handle='noFiles' class='info-noFiles'>nls(Info_NoFiles)</div>" +
				"<div handle='content' class='hidden'>" +
					"<div class='info-line'>" +
						"<span class='info-label'>nls(Info_Label_Simulator)</span>" +
						"<span class='info-value' handle='simulator'></span>" +
					"</div>" +
					"<div class='info-line'>" +
						"<span class='info-label'>nls(Info_Label_Files)</span>" +
						"<span class='info-value' handle='files'></span>" +
					"</div>" +
					"<div class='info-line'>" +
						"<span class='info-label'>nls(Info_Label_Name)</span>" +
						"<span class='info-value' handle='name'></span>" +
					"</div>" +
					"<div class='info-line'>" +
						"<span class='info-label'>nls(Info_Label_Size)</span>" +
						"<span class='info-value' handle='size'></span>" +
					"</div>" + 
					"<div class='info-line'>" +
						"<span class='info-label'>nls(Info_Label_NumberFrames)</span>" +
						"<span class='info-value' handle='nFrames'></span>" +
					"</div>" + 
					"<div class='info-line'>" +
						"<span class='info-label'>nls(Info_Label_LastFrame)</span>" +
						"<span class='info-value' handle='lastFrame'></span>" +
					"</div>" + 
				"</div>";
	}
	
	Clear() {
		this.Node("simulator").innerHTML = "";
		this.Node("name").innerHTML = "";
		this.Node("files").innerHTML = "";
		this.Node("size").innerHTML = "";
		this.Node("nFrames").innerHTML = "";
		this.Node("lastFrame").innerHTML = "";
		
		Dom.ToggleCss(this.Node("content"), "hidden", true);
		Dom.ToggleCss(this.Node("noFiles"), "hidden", false);
	}
	
	Initialize(info) {
		Dom.ToggleCss(this.Node("noFiles"), "hidden", true);
		Dom.ToggleCss(this.Node("content"), "hidden", false);
		
		this.UpdateLine("simulator", info.Simulator);
		this.UpdateLine("files", info.FilesAsString());
		this.UpdateLine("name", info.Name);
		this.UpdateLine("size", info.SizeAsString());
		this.UpdateLine("nFrames", info.NFrames);
		this.UpdateLine("lastFrame", info.LastFrame);
	}
	
	UpdateLine(id, value) {
		if (!value) Dom.ToggleCss(this.Node(id).parentNode, "hidden", true);
		
		else {
			Dom.ToggleCss(this.Node(id).parentNode, "hidden", false);
			
			this.Node(id).innerHTML = value;
		}
	}
});