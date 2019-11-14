'use strict';

import Lang from '../utils/lang.js';
import Array from '../utils/array.js';
import Dom from '../utils/dom.js';
import Evented from '../components/evented.js';

export default class Automated extends Evented { 
	
	get Simulation() { return this.simulation; }
	
	get Widget() { return this.widget; }

	constructor(widget, simulation) {
		super();
		
		this.simulation = simulation;
		this.widget = widget;
		this.handles = [];
	}

	Refresh() {
		throw new Error("Automated widgets must implement an AutoResize function.");
	}
	
	Handle(handles) {
		this.handles = this.handles.concat(handles);
	}
	
	Destroy() {
		Array.ForEach(this.handles, function(h) {
			h.target.Off(h.type, h.callback);
		});
		
		this.simulation = null;
		this.widget = null;
		this.handles = null;
	}
	
	Place(container) {
		this.Widget.Place(container);
	}
	
	Save() {
		return null;
	}
};