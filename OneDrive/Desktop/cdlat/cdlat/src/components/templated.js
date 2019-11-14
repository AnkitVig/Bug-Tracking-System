'use strict';

import Lang from '../utils/lang.js';
import Array from '../utils/array.js';
import Dom from '../utils/dom.js';
import Evented from './evented.js';

export default class Templated extends Evented { 

	constructor(container, options) {
		super();
		
		this.options = options || { };
				
		this.BuildTemplate();
		
		if (this.template) this.SetNamedNodes();
	
		if (this.template) this.BuildSubWidgets();
		
		if (container) this.Place(container);
	}
	
	BuildTemplate() {
		// Use template provided in options first, use Template function second
		var html = this.options.template ? this.options.template : this.Template();
		
		// TODO : I think it still works with empty templates.
		if (!html) return;
		
		// Trailing whitespaces can cause issues when parsing the template, remove them
		html = html.trim();
		
		// Replace all nls strings in template. Nls string pattern in templates is nls(StringId)
		html = this.Replace(html, /nls\((.*?)\)/, function(m) { return Lang.Nls(m); });
		
		this.template = Dom.Create("template", { innerHTML:html });
	}
	
	SetNamedNodes() {		
		var named = this.template.content.querySelectorAll("[handle]");
		
		this.nodes = {};
		
		// Can't use Array ForEach here since named is a NodeList, not an array
		for (var i = 0; i < named.length; i++) { 
			var name = Dom.GetAttribute(named[i], "handle");
			
			this.nodes[name] = named[i];
		}
	}
	
	BuildSubWidgets() {
		var nodes = this.template.content.querySelectorAll("[widget]");
		var targets = {};
		
		// Can't use Array ForEach here since nodes is a NodeList, not an array
		for (var i = 0; i < nodes.length; i++) {
			var path = Dom.GetAttribute(nodes[i], "widget");
			var module = Lang.Templatable(path);
			var widget = new module(nodes[i]);
			var handle = Dom.GetAttribute(widget.container, "handle");
			
			if (handle) this.nodes[handle] = widget;
		}
	}
	
	Place(container) {
		this.container = container;
		
		if (!this.template) return;
		
		while (this.template.content.children.length > 0) {
			Dom.Place(this.template.content.children[0], this.container);
		}
	}
	
	Template() {
		return null;		
	}

	Replace(str, expr, delegate) {
		var m = str.match(expr);
		
		while (m) {
			str = str.replace(m[0], delegate(m[1]));
			m = str.match(expr);
		}
		
		return str;
	}
	
	Node(id) {
		return this.nodes[id];
	}
}