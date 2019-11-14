'use strict';

import State from './state.js';
import Array from '../utils/array.js';

export default class Cache { 

	constructor() {
		this.n = 0;
		this.states = [];
	}
	
	Build(n, frames, models) {
		this.n = n;
		
		var state = State.Zero(models);
		
		for (var i = 0; i < frames.length; i++) {
			state.ApplyTransitions(frames[i]);

			if (i % n === 0) this.AddState(state);
		}
		
		if (i % n != 0) this.AddState(state);
	}
	
	GetClosest(i) {
		var diff = i % this.n;
		
		var state = this.GetState((i - diff) / this.n);
		
		return state;
	}
	
	get Length(){
		return this.states.length;
	}
	
	get N(){
		return this.n;
	}
	
	AddState(state) {
		this.states.push(state.Clone());
	}
	
	GetState(i) {
		return this.states[i].Clone();
	}
	
	First() {
		return this.GetState(0);
	}
	
	Last() {
		return this.GetState(this.Length - 1);
	}
}