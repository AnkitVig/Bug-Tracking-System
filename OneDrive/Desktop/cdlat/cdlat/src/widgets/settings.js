'use strict';

import Lang from '../utils/lang.js';
import Dom from '../utils/dom.js';
import Widget from '../ui/widget.js';
import oSettings from '../simulation/settings.js';

export default Lang.Templatable("Widget.Settings", class Settings extends Widget { 
	
	get Settings() { return this.settings; }

	constructor(id) {
		super(id);
		
		this.settings = new oSettings();
			
		this.settings.On("Session", this.onSettingsSession_Handler.bind(this));
			
		this.Node("size").addEventListener("change", this.onSizeChange_Handler.bind(this));
		this.Node("fps").addEventListener("change", this.onFPSChange_Handler.bind(this));
		this.Node("loop").addEventListener("change", this.onLoopChange_Handler.bind(this));
		this.Node("showGrid").addEventListener("change", this.onShowGridChange_Handler.bind(this));
		
		this.UpdateUI();
	}
	
	UpdateUI() {
		this.Node("fps").value = this.settings.FPS; 
		this.Node("size").value = this.settings.RowHeight; 
		this.Node("loop").checked = this.settings.Loop; 
		this.Node("showGrid").checked = this.settings.ShowGrid; 
	}
	
	Template() {
		return	"<div class='settings-title'>nls(Settings_Title)</div>" +
				"<div class='settings-line'>" +
					"<label class='settings-label'>nls(Settings_Size)</label>" +
					"<input class='settings-value' handle='size' type='number'></input>" +
				"</div>" +
				"<div class='settings-line'>" +
					"<label class='settings-label'>nls(Settings_FPS)</label>" +
					"<input class='settings-value' handle='fps' type='number'></input>" +
				"</div>" +
				"<div class='settings-line'>" +
					"<label for='s-loop' class='settings-label'>nls(Settings_Loop)</label>" +
					"<input id='s-loop' class='settings-value' handle='loop' type='checkbox'></input>" +
				"</div>"+
				"<div class='settings-line'>" +
					"<label for='s-showGrid' class='settings-label'>nls(Settings_ShowGrid)</label>" +
					"<input id='s-showGrid' class='settings-value' handle='showGrid' type='checkbox' disabled></input>" +
				"</div>";
	}
	
	onSizeChange_Handler(ev) {
		this.settings.RowHeight = +ev.target.value;
	}
	
	onFPSChange_Handler(ev) {
		this.settings.FPS = +ev.target.value;
	}
	
	onLoopChange_Handler(ev) {
		this.settings.Loop = ev.target.checked;
	}
	
	onShowGridChange_Handler(id, ev) {
		this.settings.ShowGrid = ev.target.checked;
	}
	
	onSettingsSession_Handler(ev) {
		this.UpdateUI();
	}
});