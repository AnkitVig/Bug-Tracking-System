'use strict';

export default class Transition { 

	constructor(id, value, diff) {

		this.id = id;
		this.value = value;
		this.diff = diff;
	}

	get Id() {
		return this.id;
	}

	get Value() {
		return this.value;
	}
	
	get Diff() {
		return this.diff;
	}
	
	set Diff(value) {
		this.diff = value;
	}
}