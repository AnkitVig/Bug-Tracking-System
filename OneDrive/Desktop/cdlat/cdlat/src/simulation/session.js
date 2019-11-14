'use strict';

import Evented from '../components/evented.js';
import Lang from '../utils/lang.js';
import Array from '../utils/array.js';

export default class Session extends Evented { 
			
	constructor(simulation, dashboard, control) {
		super();
		
		this.simulation = simulation;
		this.dashboard = dashboard;
		this.control = control;
		this.settings = control.Settings;
	}
	
	Save() {
		return JSON.stringify({ 
			simulation : this.simulation.Save(),
			dashboard : Array.Map(this.dashboard.cells, function(c) { return c.Save(); }), 
			settings : this.settings.Save(),
			control : this.control.Save()
		});
	}
	
	Load(config) {		
		this.settings.Load(config.settings);
		this.simulation.Load(config.simulation);
		this.control.Load(config.control);
		
		Array.ForEach(config.dashboard, function(c) {
			var definition = Lang.Templatable(c.name);
			var cell = this.dashboard.AddCell();
			var widget = new definition(c.config, this.simulation);
			
			cell.span = c.span;
			
			cell.AdjustCss();
			cell.Empty();
			cell.SetWidget(widget);
		}.bind(this));
		
		this.dashboard.Resize();
	}
}