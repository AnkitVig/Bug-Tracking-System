'use strict';

import Sim from '../utils/sim.js';
import Dom from '../utils/dom.js';
import Lang from '../utils/lang.js';
import Array from '../utils/array.js';
import Widget from '../ui/widget.js';
import Popup from '../ui/popup.js';
import Dropzone from './dropzone.js';
import Info from './info.js';
import Settings from './settings.js';
import Palette from './palette.js';
import Playback from './playback.js';
import Simulation from '../simulation/simulation.js';

const BG_NORMAL = "var(--color-5)";
const BG_DISABLED = "var(--color-7)";

export default Lang.Templatable("Widget.Control", class Control extends Widget { 

	get Settings() { return this.Node("settings").Settings; }
	
	get Simulation() { return this.simulation; }
	
	get Config() { return this.config; }
	
	constructor(node) {		
		super(node);
		
		this.config = null;
		this.parser = null;
		this.collapsed = false;
		
		this.Node("dbSave").addEventListener("click", this.onDbSaveClick_Handler.bind(this));
		this.Node("min").addEventListener("click", this.onCollapseClick_Handler.bind(this));
		this.Node("load").addEventListener("click", this.onLoadClick_Handler.bind(this));
		this.Node("palette").addEventListener("click", this.onPaletteClick_Handler.bind(this));
		this.Node("dropzone").On("Change", this.onDropzoneChange_Handler.bind(this));
		
		this.popup = new Popup();
		
		this.popup.Widget = new Palette();
		
		this.popup.Node("title").innerHTML = Lang.Nls("Control_PaletteEditor");
	}
	
	LoadSimulation(data) {
		this.simulation = new Simulation();
		
		this.simulation.On("Error", this.onError_Handler.bind(this));
		
		this.simulation.LoadData(this.Settings.Cache, data);
		
		this.popup.Widget.Initialize(this.simulation);
		
		this.Node("playback").Initialize(this.simulation, this.Settings);
		this.Node("info").Initialize(this.simulation.info);	
		
		this.Node("load").disabled = false;
		this.Node("dbSave").disabled = false;
		
		Dom.RemoveCss(this.Node("palette"), "disabled");
		
		Dom.ToggleCss(this.Node("load"), "loading", false);
		
		this.Emit("Ready", { simulation:this.simulation, config:this.config });
	}
	
	SetCollapsed(isCollapsed) {
		this.collapsed = isCollapsed;
		
		var icon = this.collapsed ? "fas fa-caret-down" : "fas fa-caret-up";
		
		Dom.ToggleCss(this.container, "collapsed", this.collapsed);
		Dom.SetCss(this.Node("icon"), icon);
	}
	
	onDropzoneChange_Handler(ev) {
		this.Node("playback").Stop();
		
		var success = this.onParserDetected_Handler.bind(this);
		var failure = this.onError_Handler.bind(this);
		
		Sim.DetectParser(ev.files).then(success, failure);
	}
	
	onParserDetected_Handler(ev) {
		this.parser = ev.result.parser;
		
		var fileList = this.Node("dropzone").files;
		var name = `${this.parser.ModelName}.json`;
		var file = Array.Find(fileList, function(f) { return f.name.match(name); });
		
		var success = this.onConfigParsed_Handler.bind(this);
		var failure = this.onError_Handler.bind(this);
		
		Sim.ReadFile(file, (d) => { return JSON.parse(d); }).then(success, failure);
	}
	
	onConfigParsed_Handler(ev) {
		this.config = ev.result;
		
		this.parser.On("Progress", this.onParserProgress_Handler.bind(this));
		
		this.Node("load").disabled = false;
	}
	
	onLoadClick_Handler() {
		this.Node("load").disabled = true;
		
		Dom.ToggleCss(this.Node("load"), "loading", true);
		
		this.Node("playback").Stop();
		
		this.parser.Parse().then((ev) => { this.LoadSimulation(ev.result); });
	}
	
	onParserProgress_Handler(ev) {
		var bg = `linear-gradient(to right, ${BG_NORMAL} 0%, ${BG_NORMAL} ${ev.progress}%, ${BG_DISABLED} ${ev.progress}%, ${BG_DISABLED} 100%)`;
		
		this.Node("load").style.backgroundImage = bg;
	}
	
	onCollapseClick_Handler() {		
		this.SetCollapsed(!this.collapsed);
	}
	
	onDbSaveClick_Handler() {				
		this.Emit("Save");
	}
	
	onDbLoadClick_Handler() {				
		this.Emit("Load");
	}

	onError_Handler(ev) {
		// TODO : Probably handle error here, alert message or something.
		this.Node("dropzone").Reset();
		
		alert(ev.error.toString())
	}
	
	onPaletteClick_Handler(ev) {
		if (this.simulation) this.popup.Show();
	}
	
	Save() {
		return { collapsed:this.collapsed }
	}
	
	Load(config) {
		this.SetCollapsed(config.collapsed);
	}

	Template() {
		return "<div class='control row'>" +
				  "<div class='dash-box'>" +
					  "<div class='row row-0'>" +
						 "<button handle='min' class='collapse'><i handle='icon' class='fas fa-caret-up'></i></button>" +
					  "</div>" +

					  "<div class='row row-1'>" +
						  "<div handle='dropzone' class='dropzone' widget='Widget.Dropzone'></div>" +
						  "<div handle='info' class='info' widget='Widget.Info'></div>" +
						  "<div handle='settings' class='settings' widget='Widget.Settings'></div>" +
						  "<img handle='palette' class='palette disabled' src='assets/swatch.png' title='nls(Control_PaletteEditor)' alt='nls(Control_PaletteEditor)'>" +
					  "</div>" +
					  
					  "<div class='row row-2'>" +
						  "<button handle='load' class='load' disabled>nls(Main_Load)</button>" +
						  "<div handle='playback' class='playback' widget='Widget.Playback'></div>" +
						  "<button handle='dbSave' class='save' disabled>nls(Main_DashBoard_Save)</button>" +
					  "</div>" +
				  "</div>" +
			   "</div>";
	}
});