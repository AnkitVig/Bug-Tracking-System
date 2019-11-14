'use strict';

import Widget from '../ui/widget.js';
import Lang from '../utils/lang.js';
import Dom from '../utils/dom.js';

export default Lang.Templatable("UI.Last", class Last extends Widget { 

	get Root() { return this.Node("cell"); }

	constructor(container) {
		super(container);		
		
		this.Node("button").addEventListener("click", this.onButtonClick_Handler.bind(this));
	}
		
	Resize(size) {
		this.Node("cell").style.height = size + 'px';
	}
	
	onButtonClick_Handler(ev) {
		this.Emit("Click", {});
	}
	
	Template() {
		return "<div handle='cell' class='cell last span-1'>" +
				  "<div class='dash-box'>" + 
					 "<i handle='button' class='fas fa-plus-circle'></i>" +
				  "</div>" + 
			   "</div>";
	}
});