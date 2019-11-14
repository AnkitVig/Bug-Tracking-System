'use strict';

import State from './state.js';
import Array from '../utils/array.js';
import Evented from '../components/evented.js';

export default class Selection extends Evented { 

	get Selected() { return this.selected; }

	constructor() {
		super();

		this.selected = [];
	}
	
	IsSelected(mod) {
		return Array.Has(this.selected, function(s) { 
			return s == mod;
		});
	}
	
	Select(mod) {
		this.selected.push( mod );
		
		this.Emit("Change", { mod, selected:true });
	}
	
	Deselect(mod) {
		var idx = Array.FindIndex(this.selected, function(s) { 
			return s == mod;
		});
		
		this.selected.splice(idx, 1);
		
		this.Emit("Change", { mod, selected:false });
	}
	
	Save() {
		return this.selected;
	}
	
	Load(config) {
		Array.ForEach(config, function(sel) {
			this.selected.push( sel );
		}.bind(this));
		
		this.Emit("Session", { selection:this });		
	}
}