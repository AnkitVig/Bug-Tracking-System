'use strict';

import Templated from '../components/templated.js';
import Array from '../utils/array.js';
import Dom from '../utils/dom.js';

export default class Widget extends Templated { 

	constructor(container) {
		super(container);
	}
	
	GetContainerSize() {
		return Dom.Geometry(this.container);
	}
	
	Refresh() {
		
	}
}