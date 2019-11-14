'use strict';

import Lang from '../utils/lang.js';
import Array from '../utils/array.js';
import Dom from '../utils/dom.js';
import Widget from '../ui/widget.js';

export default Lang.Templatable("Auto.TransitionChart", class AutoTransitionChart extends Widget { 

	constructor(id) {
		super(id);
	}
	
	Template() {
		return "<div></div>"
	}
});