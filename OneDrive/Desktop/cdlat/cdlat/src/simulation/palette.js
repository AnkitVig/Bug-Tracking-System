'use strict';

import Evented from '../components/evented.js';
import Array from '../utils/array.js';
import Sim from '../utils/sim.js';

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
		var clss = { i:this.classes.length, start:start, end:end, color:color };
		
		this.classes.push(clss);
		
		return clss;
	}
	
	RemoveClass(clss) {
		var i = this.classes.indexOf(clss);
		
		this.classes.splice(i, 1);
		
		this.Emit("Change", { palette:this });
	}
	
	GetColor(value) {
		var clss = Array.Find(this.classes, function(c) {
			return (value >= c.start && value < c.end);
		});
		
		var color = clss ? clss.color : [248, 24, 148];
		
		return `rgb(${color.join(",")})`;
	}
	
	SetColor(clss, color) {
		clss.color = color;
		
		this.Emit("Change", { palette:this });
	}
	
	SetStart(clss, value) {
		clss.start = value;
		
		this.Emit("Change", { palette:this });
	}
	
	SetEnd(clss, value) {
		clss.end = value;
		
		this.Emit("Change", { palette:this });
	}
	
	Save() {
		return {
			fileName : this.fileName,
			classes : this.classes
		}
	}
	
	Load(config) {
		this.fileName = config.fileName;
		this.classes = config.classes;
		
		this.Emit("Session", { palette:this });
	}
	
	Buckets(n, cMin, cMax, vMin, vMax) {
		var c1 = Sim.HexToRgb(cMin);
		var c2 = Sim.HexToRgb(cMax);
		
		var d = (vMax - vMin) / n;
		
		var dR = (c2[0] - c1[0]) / (n - 1);
		var dG = (c2[1] - c1[1]) / (n - 1);
		var dB = (c2[2] - c1[2]) / (n - 1);
		
		this.classes = [];
		
		for (var i = 0; i < n; i++) {
			var v1 = vMin + i * d;
			var v2 = vMin + (i + 1) * d;
			
			var r = c1[0] + dR * i;
			var g = c1[1] + dG * i;
			var b = c1[2] + dB * i;
			
			this.classes.push({ i:classes.length, start:v1, end:v2, color:[r,g,b] });
		}
	
		this.Emit("Change", { palette:this });
	}
}