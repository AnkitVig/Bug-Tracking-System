'use strict';

import Array from '../utils/array.js';
import Transition from './transition.js';

export default class Frame { 

	constructor(time) {
		this.time = time;
		this.transitions = [];
		this.index = {};
	}
	
	get Length() {
		return this.transitions.length;
	}
	
	AddTransition(id, value, diff) {
		var t = new Transition(id, value, diff);
		
		this.index[t.id] = t;
		
		this.transitions.push(t);
	}
	
	TransitionById(id) {
		return this.index[id] || null;
	}
	
	Transition(i) {
		return this.transitions[i];
	}
	
	First() {
		return this.transitions[0];
	}
	
	Last()  {
		return this.transitions[this.transitions.length - 1];
	}
	
	Reverse () {
		var reverse = new Frame(this.time)
		
		Array.ForEach(this.transitions, function(t) {
			reverse.AddTransition(t.id, t.value - t.diff, t.diff);
		})
		
		return reverse;
	}
	
	Difference(state) {
		for (var i = 0; i < this.Length; i++) {
			var t = this.Transition(i);
			 
			t.diff = t.value - state.GetValue(t.id);
			
			state.SetValue(t.id, t.value);
		}
	}
}