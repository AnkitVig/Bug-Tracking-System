'use strict';

export default class SimulationInfo { 

	get Name() { return this.name; }
	set Name(value) { this.name = value; }
	
	get Files() { return this.files; }
	set Files(value) { this.files = value; }
	
	get Size() { return this.size; }
	set Size(value) { this.size = value; }
	
	get Simulator() { return this.simulator; }
	set Simulator(value) { this.simulator = value; }
	
	get NFrames() { return this.nFrames; }
	set NFrames(value) { this.nFrames = value; }
	
	get LastFrame() { return this.lastFrame; }
	set LastFrame(value) { this.lastFrame = value; }

	constructor() {
		this.name = null;
		this.files = null;
		this.size = { x:0, y:0 };
		this.simulator = null;
		this.nFrames = null;
		this.lastFrame = null;
	}
	
	Load(simulation, parser) {
		this.Simulator = parser.Type;
		this.Name = parser.ModelName;
		this.Files = parser.FileNames;
		this.LastFrame = simulation.LastFrame().id;
		this.NFrames = simulation.frames.length;

		var t = simulation.FirstFrame().Last().id;
		if(t.search("-") != -1){
			t = t.split("-");
			this.size = { x:parseInt(t[0]) + 1, y:parseInt(t[1]) + 1, z:parseInt(t[2]) + 1 };
		}
		else{
			this.size = simulation.ModelLength + 1;
		}

	}
	
	Configured() {
		return this.Simulator != null;
	}
	
	Loaded() {
		return this.Simulator != null;
	}
	
	SizeAsString() {
		if (!this.size) return null;
		if(this.size.hasOwnProperty('x')){
			return `${this.size.x}, ${this.size.y}, ${this.size.z}`;
		}
		else{
			return `${this.size}`;
		}

	}
	
	FilesAsString() {		
		return this.files && this.files.join(", ");
	}
}