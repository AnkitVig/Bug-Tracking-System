'use strict';

import Array from '../../utils/array.js';
import Lang from '../../utils/lang.js';
import Dom from '../../utils/dom.js';
import Widget from '../../ui/widget.js';
import Auto from '../auto.js';

// TODO : should implement a placeable and saveable interface or something
export default Lang.Templatable("Widget.Selector", class Selector extends Widget { 

	constructor(id) {
		super(id);
		
		this.current = null;
		
		this.items = Array.Map(Auto.Widgets(), function(w) {
			var options = { className:"definition-line", innerHTML:Auto.Label(w.id) };
			
			var item = {
				node : Dom.Create("div", options, this.Node("select")),
				definition : Auto.Definition(w.id),
				configurator : Auto.Configurator(w.id)
			}
			
			item.node.addEventListener("click", this.onDefinitionClick_Handler.bind(this, item));
			
			return item;
		}.bind(this)); 
		
		this.onLoadClick_Handler = this.onLoadClick_Handler.bind(this);
		
		this.Node("load").addEventListener("click", this.onLoadClick_Handler);
	}
	
	Destroy() {
		this.Node("load").removeEventListener("click", this.onLoadClick_Handler);
	}
	
	onDefinitionClick_Handler(item, ev) {
		if (this.current) Dom.RemoveCss(this.current.node, "selected");
		
		this.current = item;
		
		Dom.AddCss(this.current.node, "selected");
	}
	
	onLoadClick_Handler(ev) {
		if (!this.current) return;
				
		this.Emit("Load", { definition:this.current.definition, configurator:this.current.configurator  });
	}
	
	Template() {
		return	"<div class='selector'>" +
					"<div class='title-container'>" +
						"<div class='selector-title'>nls(Selector_Title)</div>" +
					"</div>" +
					"<div class='select-container'>" + 
						"<div handle='select' class='select'></div>"+
					"</div>" +
					"<div class='load-container'>" +
						"<button handle='load' class='load'>nls(Selector_Load)</button>" +
					"</div>" +
				"</div>";
	}
});