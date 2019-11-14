'use strict';

import Widget from '../ui/widget.js';
import Lang from '../utils/lang.js';
import Dom from '../utils/dom.js';

export default Lang.Templatable("UI.Cell", class Cell extends Widget { 

	get Root() { return this.Node("cell"); }
		
	get Widget() { return this.widget; }
	
	constructor(container) {
		super(container);
		
		this.widget = null; 
		this.span = 1;
		
		this.Node("left").addEventListener("click", this.onLeftClick_Handler.bind(this));
		this.Node("right").addEventListener("click", this.onRightClick_Handler.bind(this));
		this.Node("close").addEventListener("click", this.onCloseClick_Handler.bind(this));
	}
		
	SetWidget(widget) {
		this.widget = widget;
		
		widget.Place(this.Node("content"));
	}
	
	Empty() {
		this.widget = null;
		
		Dom.Empty(this.Node("content"));
	}
	
	Resize(size) {
		if (size != undefined) this.Node("cell").style.height = size + 'px';
		
		if (!this.widget) return;
		
		this.widget.Refresh();
	}
	
	onLeftClick_Handler() {
		if (this.span == 1) return;
		
		this.span--;
		
		this.AdjustCss();
		
		this.Emit("Span", { cell:this });
	}
	
	onRightClick_Handler() {
		if (this.span == 4) return;
		
		this.span++;
		
		this.AdjustCss();
		
		this.Emit("Span", { cell:this });
	}
	
	onCloseClick_Handler() {		
		this.Emit("Close", { cell:this });
	}
	
	AdjustCss() {
		Dom.SetCss(this.Node("cell"), `cell span-${this.span}`);
		Dom.SetCss(this.Node("left"), `expand left`);
		Dom.SetCss(this.Node("right"), `expand right`);
		
		if (this.span == 1) Dom.AddCss(this.Node("left"), 'limit');
		if (this.span == 4) Dom.AddCss(this.Node("right"), 'limit');
	}
	
	Save() {
		var saved = this.Widget.Save();
		
		saved.span = this.span;
		
		return saved;
	}
	
	Template() {
		return "<div handle='cell' class='cell span-1'>" +
				  "<div class='dash-box'>" + 
					"<div handle='close' class='close'>" + 
						"<i class='fas fa-times-circle'></i>" + 
					"</div>" +
					"<div handle='content' class='cell-content'></div>" +
					"<div handle='right' class='expand right'>" + 
						"<i class='fas fa-caret-right'></i>" + 
					"</div>" +
					"<div handle='left' class='expand left limit'>" + 
						"<i class='fas fa-caret-left'></i>" + 
					"</div>" +
				  "</div>" + 
			   "</div>";
	}
});