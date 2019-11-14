'use strict';

import Lang from '../utils/lang.js';
import Dom from '../utils/dom.js';
import Widget from './widget.js';

export default Lang.Templatable("UI.Tooltip", class Tooltip extends Widget {
			
	constructor(container) {				
		super(container);
	}
	
	Template() {
		return '<div handle="root" class="tooltip">' +
				  '<div handle="content"></div>' +
			   '</div>';
	}
					
	PositionTarget(target, offset) {
		offset = offset || [0,0];
		
		bbox1 = target.getBoundingClientRect();
		bbox2 = this.Node("root").getBoundingClientRect();
		
		var x = bbox1.left +  bbox1.width / 2 - bbox2.width / 2 + offset[0];
		var y = bbox1.top + document.documentElement.scrollTop - bbox2.height - 5  + offset[1];
		
		this.PositionXY(x, y);
	}
	
	PositionXY(x, y) {
		this.Node("root").style.left = x + "px";
		this.Node("root").style.top = y + "px";
				
		if (this.BBox.left + this.BBox.width > window.innerWidth) {
			this.Node("root").style.top = y + 30 + "px";
			this.Node("root").style.left = -180 + x + "px";
		}
	}
	
	Show(x, y) {
		this.PositionXY(x, y);
		
		this.Node("root").style.opacity = 1;
	}
	
	Hide() {
		this.Node("root").style.opacity = 0;
	}
	
	Empty() {
		Dom.Empty(this.Node("content"));
	}
	
	get BBox() {
		return this.Node("root").getBoundingClientRect();
	}
});