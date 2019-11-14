'use strict';

import Lang from '../utils/lang.js';
import Dom from '../utils/dom.js';
import Widget from '../ui/widget.js';

export default Lang.Templatable("Widget.Header", class Header extends Widget { 

	constructor(id) {
		super(id);
	}
	
	Template() {
		return	"<h1 handle='lab' class='row'>nls(Header_Lab)</h1>" +
				"<div class='row'>" +
					"<div class='header-app column'>" +
						"<h1 handle='app' class='d-inline'>nls(Header_App)</h1>" +
					"</div>" +
					"<div>" +
						"<a handle='tutorial' href='https://goo.gl/vhQE03' target='_blank'>nls(Header_Tutorial)</a> &emsp;" +
						"<a handle='sample' href='https://goo.gl/S7agHi' target='_blank'>nls(Header_Sample)</a> &emsp;" +
						"<a handle='problem' href='mailto:bruno.st-aubin@carleton.ca?Subject=[CellDEVSViewer][alpha]' target='_blank'>nls(Header_Problem)</a>" +
					"</div>" +
				"</div>";
	}
});