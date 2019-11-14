'use strict';

import Lang from './lang.js';
import Array from './array.js';

export default class Dom {
	
	static Node(pNode, selector) {
		return pNode.querySelectorAll(selector).item(0) || null;
	}

	static Create(tagName, options, pNode) {
		var elem = document.createElement(tagName);
		
		Lang.Mixin(elem, options);
		
		this.Place(elem, pNode);
		
		return elem
	}

	static CreateSVG(tagName, options, pNode) {
		var elem = document.createElementNS("http://www.w3.org/2000/svg", tagName);
		
		for (var id in options) elem.setAttribute(id, options[id]);
		
		this.Place(elem, pNode);
		
		return elem;
	}

	static CreateNS(ns, tagName, options, pNode) {
		var elem = document.createElementNS(ns, tagName);
		
		for (var id in options) elem.setAttribute(id, options[id]);
		
		this.Place(elem, pNode);
		
		return elem;
	}

	static Place(elem, pNode) {
		if (!!pNode) pNode.appendChild(elem);
	}

	static Replace(elem1, elem2) {
		var pNode = elem1.parentNode;
		
		pNode.insertBefore(elem2, elem1);

		this.Remove(elem1, pNode);
	}

	static Remove(elem, pNode) {
		if (!Array.Has(pNode.children, function(child) { return (child === elem); })) return;
		
		pNode.removeChild(elem);
	}

	static Empty(elem) {
		while (elem.firstChild) {
			elem.removeChild(elem.firstChild);
		}
	}

	static AddCss(domNode, css) {
		var c1 = domNode.className.split(" ");
		var c2 = css.split(" ");
		
		var classes = Array.UniqueConcat(c1, c2);
		
		domNode.className = classes.join(" "); 
	}

	static RemoveCss(domNode, css) {				
		var c1 = domNode.className.split(" ");
		var c2 = css.split(" ");
		
		var classes = Array.Filter(c1, function(c) { return c2.indexOf(c) == -1; });
		
		domNode.className = classes.join(" "); 
	}

	static HasCss(domNode, css) {
		return (' ' + domNode.className + ' ').indexOf(' ' + css + ' ') > -1;
	}

	static SetCss(domNode, css) {
		domNode.className = css; 
	}

	static ToggleCss(domNode, css, enabled) {
		if (enabled) this.AddCss(domNode, css);
		
		else this.RemoveCss(domNode, css);
	}
	
	static GetAttribute(domNode, attr) {
		var attr = domNode.attributes.getNamedItem(attr);
		
		return attr ? attr.value : null;
	}
	
	static SetAttribute(domNode, attr, value) {
		domNode.setAttribute(attr, value);
	}
	
	static Geometry(node) {
		var style = window.getComputedStyle(node);
		
		var h = +(style.getPropertyValue("height").slice(0, -2));
		var w = +(style.getPropertyValue("width").slice(0, -2));
		var pL = +(style.getPropertyValue("padding-left").slice(0, -2));
		var pR = +(style.getPropertyValue("padding-right").slice(0, -2));
		var pT = +(style.getPropertyValue("padding-top").slice(0, -2));
		var pB = +(style.getPropertyValue("padding-bottom").slice(0, -2));
		
		var w = w - pL - pR;
		var h = h - pT - pB;
		
		// Use smallest width as width and height for square grid that fits in container
		// var s = w < h ? w : h;
		
		return { w : w , h : h }
	}
}