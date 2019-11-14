'use strict';

import Lang from '../../utils/lang.js';
import Array from '../../utils/array.js';
import Evented from '../../components/evented.js';
import ChunkReader from '../../components/chunkReader.js';
import Frame from '../frame.js';

const PARSERS = [];

export default class Parser extends Evented { 

	get FileNames() { 	
		var names = [];
		
		for (var id in this.files) {
			if (this.files[id]) names.push(this.files[id].raw.name);
		}
		
		return names;
	}
	
	get Type() { 
		throw new Error("Parsers must implement a get accessor for Type");
	}
	
	get ModelName() { 
		throw new Error("Parsers must implement a get accessor for ModelName");
	}
	
	
	constructor() {
		super();

	}
	/*
	GetJsonFile (fileList) {
		var file = Array.Find(fileList, function(f) { return f.name.match(".json"); });
		
		return file ? { id:"cfg", raw:file, content: null } : null;
	}
	*/
	GetFiles(fileList) {	
		throw new Error("Parsers must implement a GetFiles(fileList) function");
	}
	
	IsValid() {
		throw new Error("Parsers must implement a IsValid() function");
	}
	
	GetSimulationInfo() {
		throw new Error("Parsers must implement a GetSimulationInfo() function");
	}
	
	GetPalette() {
		throw new Error("Parsers must implement a GetPalette() function");
	}
	
	GetFrames() {
		throw new Error("Parsers must implement a GetFrames() function");
	}
	GetModels() {
		throw new Error("Parsers must implement a GetModels() function");
	}
	
	// GetConfig() {
	//	return this.files.cfg ? this.files.cfg.content : null;
	// }
	
	ParseTasks() {
		throw new Error("Parsers must implement a ParseTasks() function");
	}
	
	Parse() {
		var d = Lang.Defer();
		var defs = this.ParseTasks();
		
		// defs.push(this.ReadFile("cfg", (d) => { return JSON.parse(d); }));
		
		Promise.all(defs).then((results) => {		
			d.Resolve({
				// config : this.GetConfig(),
				frames : this.GetFrames(),
				palette : this.GetPalette(),
				models : this.GetModels(),
				parser : this
			});
		});
		
		return d.promise;
	}
	
	Initialize(fileList) {
		this.files = this.GetFiles(fileList);
		
		// this.files.cfg = this.GetJsonFile(fileList);
		
		return this.IsValid();
	}
	
	/*
	ReadFile(id, parseFn) {
		var d = Lang.Defer();
		var reader = new ChunkReader();
		var file = this.files[id];
		
		if (!file) d.Resolve(null);
		
		else {
			reader.Read(file.raw).then((ev) => { 
				file.content = parseFn(ev.result); 
				
				d.Resolve(file.content);
			});
		}
		
		return d.promise;
	}
	*/
}