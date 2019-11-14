'use strict';

import Lang from '../utils/lang.js';

export default class Chart { 

	constructor(pNode) {
		this.pNode = pNode;
		
		this.groups = {};
		this.axis = {};
		this.scales = {};
		this.labels = {};
		
		this.size = { w:0, h:0 };
		
		this.svg = d3.select(pNode)
					 .append("svg")
					 .attr("class", "chart-svg")
					 .attr("preserveAspectRatio", "xMidYMid meet")
					 // .attr("viewBox", "0 0 1000 1000"];
	}
	
	Group(id) {
		return this.groups[id];
	}
	
	Scale(id) {
		return this.scales[id];
	}
	
	Axis(id) {
		return this.axis[id];
	}
	
	Label(id) {
		return this.labels[id];
	}
	
	AddGroup(id) {
		if (this.groups[id]) throw new Error("group already exists in chart");
		
		var g = this.svg.append("g")
						.classed(`group-${id}`, true);
		
		this.groups[id] = g;
		
		return g;
	}
	
	AddAxis(id) {
		if (this.axis[id]) throw new Error("axis already exists in chart");
		
		var g = this.Group("axis");
		
		if (!g) throw new Error("axis group not created");
		
		this.axis[id] = g.append("g")
						 .attr("class", `axis axis-${id}`)

		 return this.axis[id];
	}
	
	AddScale(id, scale) {
		if (this.scales[id]) throw new Error("scale already exists in chart");
		
		this.scales[id] = scale;
		
		return scale;
	}
	
	AddLabel(id, text) {
		if (this.labels[id]) throw new Error("label already exists in chart");
		
		var g = this.Group("labels");
		
		if (!g) throw new Error("labels group not created");
		
		this.labels[id] = g.append("text").classed("label", true).text(text);
		
		return this.labels[id];
	}
		
	Resize() {
		var bbox = this.pNode.getBoundingClientRect();
		
		this.size = { w:bbox.width, h:bbox.height }
		
		this.svg.attr("width", this.size.w)
				.attr("height", this.size.h);
				
		return this.size;
	}
}