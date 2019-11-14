'use strict';

import Evented from '../../components/evented.js';
import Array from '../../utils/array.js';
import Sim from '../../utils/sim.js';

const SELECTED_COLOR = "red";

export default class Palette extends Evented { 
	
	set FileName(value) { this.fileName = value; }
	
	get SelectedColor() { return SELECTED_COLOR; }
	
	constructor(name) {
		super();
		
		this.name = name;
		this.cMin = null;
		this.cMax = null;
		this.vMin = null;
		this.vMax = null;
	}
	
	SetMinMax(vMin, cMin, vMax, cMax) {
		this.cMin = Sim.HexToRgb(cMin);
		this.cMax = Sim.HexToRgb(cMax);
		this.vMin = vMin;
		this.vMax = vMax;
		
		this.vDlt = this.vMax - this.vMin;
		this.cDlt = [this.cMax[0] - this.cMin[0], this.cMax[1] - this.cMin[1], this.cMax[2] - this.cMin[2]];
		
		this.Emit("Change", { palette:this });
	}
		
	GetColor(value) {
		var p = (value - this.vMin) / this.vDlt;
		
		var r = p * this.cDlt[0] + this.cMin[0];
		var g = p * this.cDlt[1] + this.cMin[1];
		var b = p * this.cDlt[2] + this.cMin[2];
				
		return `rgb(${r},${g},${b})`
	}
	
	Save() {
		return {
			fileName : this.fileName,
			cMin : this.cMin,
			cMax : this.cMMax,
			vMin : this.vMin,
			vMax : this.vMax
		}
	}
}