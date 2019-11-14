'use strict';

import Array from '../utils/array.js';

export default class State { 

	constructor(i, model) {
		this.i = i;
		this.model = model;
	}
	
	Clone(){
		var clone = JSON.parse(JSON.stringify(this.model));

		return new State(this.i, clone);
	}
	
	GetValue(id) {
		return this.model[id];
	}
	
	SetValue(id, value) {
		this.model[id] = value;
	}
	
	ApplyTransitions(frame) {
		Array.ForEach(frame.transitions, function(t) {
			this.SetValue(t.id, t.value);
		}.bind(this));
		
		this.i++;
	}
	
	RollbackTransitions(frame) {
		Array.ForEach(frame.transitions, function(t) {
			var value = this.GetValue(t.id) - t.diff;
			
			this.SetValue(t.id, value);
		}.bind(this));
		
		this.i--;
	}
	
	static Zero(models) {
		var model = models;

		for (var id in model) {
			model[id] = 0;
		}

			
		return new State(-1, model);
	}
}