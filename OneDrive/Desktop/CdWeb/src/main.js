'use strict';

import nls from './nls.js';
import Lang from './utils/lang.js';
import Array from './utils/array.js';
import Dom from './utils/dom.js';
import Widget from './ui/widget.js';
import Header from './widgets/header.js';
import Control from './widgets/control.js';
import Dashboard from './ui/dashboard.js';
import Session from './simulation/session.js';

export default class Main extends Widget { 

	constructor(node) {		
		Lang.locale = "en";
		Lang.nls = nls;
		
		super(node);
		
		this.Node("control").On("Ready", this.onControlReady_Handler.bind(this));
		this.Node("control").On("Save", this.onControlSave_Handler.bind(this));
		
		this.Node("dashboard").Settings = this.Node("control").Settings;
		this.Node("dashboard").On("NewWidget", this.onDashboardNewWidget_Handler.bind(this));
		this.Node("dashboard").Resize();
	}
	
	onControlReady_Handler(ev) {
		Dom.RemoveCss(this.Node("dashboard").container, "hidden");
		
		this.simulation = ev.simulation;
		
		this.simulation.On("Error", this.onError_Handler.bind(this));
		
		this.session = new Session(this.simulation, this.Node("dashboard"), this.Node("control"));
		
		this.Node("dashboard").Empty();
			
		if (ev.config) this.session.Load(ev.config);
		
		else this.Node("dashboard").AddCell();
	}
	
	onDashboardNewWidget_Handler(ev) {		
		if (ev.configurator) { 		
			ev.cell.SetWidget(new ev.configurator());
			
			ev.cell.Widget.On("Configured", this.onWidgetConfigured_Handler.bind(this, ev.cell, ev.definition));
		}
		else this.SetWidgetInCell(ev.cell, ev.definition, null);
	}

	onWidgetConfigured_Handler(cell, definition, ev) {		
		this.SetWidgetInCell(cell, definition, ev.configuration);
	}
	
	onError_Handler(ev) {
		alert(ev.error.ToString())
	}
	
	onControlSave_Handler() {
		Lang.Download(this.simulation.Info.Name + ".json", this.session.Save());
	}
	
	SetWidgetInCell(cell, definition, configuration) {
		var auto = new definition(configuration, this.simulation);
	
		cell.Empty();
		cell.SetWidget(auto);
		cell.Widget.Refresh();
	}
	
	Template() {
		return	"<div class='main'>" +
					"<div id='header' class='header row' widget='Widget.Header'></div>" +
					"<div handle='control' class='control row' widget='Widget.Control'></div>" +
					"<div handle='dashboard' class='dashboard hidden' widget='UI.Dashboard'></div>" +
				"</div>";
	}
}