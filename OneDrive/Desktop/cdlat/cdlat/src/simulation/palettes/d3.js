'use strict';

import Evented from '../../components/evented.js';
import Array from '../../utils/array.js';
import Sim from '../../utils/sim.js';

const SELECTED_COLOR = "red";

export default class Palette extends Evented { 

	set FileName(value) { this.fileName = value; }
	
	get SelectedColor() { return SELECTED_COLOR; }

	constructor(fileName) {
		super();
		
		this.fileName = fileName;
		this.classes = [];
	}
	
	AddClass(start, end, color) {		
		this.classes.push({ i:this.classes.length, start:start, end:end, color:color });
	}
	
	GetColor(value) {
		return this.gradient(value);
	}
	
	SetColor(clss, color) {
		clss.color = color;
		
		this.Emit("Change", { palette:this });
	}
	
	Save() {
		return {
			fileName : this.fileName,
			classes : this.classes
		}
	}
	
	Buckets(n, cMin, cMax, vMin, vMax) {
		var colors = [];
		var c1 = Sim.HexToRgb(cMin);
		var c2 = Sim.HexToRgb(cMax);
				
		var dR = (c2[0] - c1[0]) / (n - 1);
		var dG = (c2[1] - c1[1]) / (n - 1);
		var dB = (c2[2] - c1[2]) / (n - 1);
		
		for (var i = 0; i < n; i++) {
			var r = Math.floor(c1[0] + dR * i);
			var g = Math.floor(c1[1] + dG * i);
			var b = Math.floor(c1[2] + dB * i);
			
			colors.push(Sim.RgbToHex([r,g,b]));
		}
		
		// var colors = ["#ffffcc","#ffeda0","#fed976","#feb24c","#fd8d3c","#fc4e2a","#e31a1c","#bd0026","#800026"]; 
		
		this.gradient = d3.scaleQuantile().domain([vMin, n, vMax]).range(colors);
	}
}
				