'use strict';

import Lang from '../../utils/lang.js';
import Array from '../../utils/array.js';
import Sim from '../../utils/sim.js';
import Frame from '../frame.js';
import Parser from "./parser.js";
import Palette from '../palette.js';
import ChunkReader from '../../components/chunkReader.js';

export default class DEVS extends Parser { 
	
	get Type() { return "DEVS"; }
	
	get ModelName() { 
		var i = this.files.log.raw.name.lastIndexOf(".");
		
		return this.files.log.raw.name.substr(0, i); 
	}
	
	constructor(fileList) {
		super(fileList);
		this.frames = [];
		this.index = {};
		this.models = {};
	}
	
	GetFiles (fileList) {		
		return {
			log : Array.Find(fileList, function(f) { return f.name.match(".log"); }),
			ma : Array.Find(fileList, function(f) { return f.name.match(".ma"); }),
			//pal : Array.Find(fileList, function(f) { return f.name.match(".pal"); }),
			//val : Array.Find(fileList, function(f) { return f.name.match(".val"); })
		}
	}
	
	IsValid() {		
		var d = Lang.Defer();

		if (!this.files.log) d.Reject(new Error(`DEVS Parser is not valid for the selected files.` ));

		var reader = new ChunkReader();
		
		reader.ReadChunk(this.files.log.raw, 400).then((ev) => {

			var isValid = ev.result.indexOf(",") >= 0;
			
			if (!isValid) d.Resolve(this);
			
			d.Reject(new Error(`DEVS Parser is not valid for the selected files.`));
		});
		
		return d.promise;
	}
	
	ParseTasks() {	
		var defs = [];	
		
		defs.push(this.ParseLogFile());

		return defs;
	}
	
	GetPalette() {
		return new Palette();
	}
	
	GetFrames() {
		return this.frames;
	}

	GetModels() {
		return this.models;
	}
	
	ParseMaFile(f) {	
		var data = [];
		
		// Type A: [rangeBegin;rangeEnd] R G B
		Array.ForEach(f.split(/\n/), function(line) { 

		});
		
		return data;
	}	
	
	
	ParseLogFile() {
		this.files.log.content = [];
		
		var d = Lang.Defer();
		var reader = new ChunkReader();
		
		this.ParseChunks(reader, this.files.log, d);
		
		return d.promise;
	}
	
	ParseChunks(reader, log, defer) {
		reader.ReadChunk(log.raw).then((ev) => {
			var idx = ev.result.lastIndexOf('\n');
			var chunk = ev.result.substr(0, idx);
			this.ParseSafeChunk(chunk);
			
			reader.MoveCursor(chunk.length + 1);
			
			this.Emit("Progress", { progress: 100 * reader.position / log.raw.size });
			
			if (reader.position < log.raw.size) this.ParseChunks(reader, log, defer);
			
			else if (reader.position == log.raw.size) defer.Resolve(log.content);
			
			else throw new Error("Reader position exceeded the file size.");
		});
	}
		
	ParseSafeChunk(chunk) {
		var lines = [];
		var start = chunk.indexOf('Mensaje Y', 0);
							
		while (start > -1 && start < chunk.length) {			
			var end = chunk.indexOf('\n', start);
			
			if (end == -1) end = chunk.length + 1;
			
			var length = end - start;
			
			lines.push(chunk.substr(start, length));

			var start = chunk.indexOf('Mensaje Y', start + length);
		}
		
		var safe = [];
		
		Array.ForEach(lines, function(line) {
			var split = line.split("/");
			// Parse coordinates
			var i = split[2].indexOf('(');
			var j = split[2].indexOf(')');
			var c = split[2].substring(i + 1, j).split(',');
			
			// TODO : Does this ever happen?
			if (c.length > 1) return;

			//var coord = { x:parseInt(c[1],10), y:parseInt(c[0],10), z:parseInt(c.length==3 ? c[2] : 0, 10) }
			//var coord = { x:parseInt(c[0],10), y:parseInt(c[0],10), z:parseInt(c[0],10) }
			var model = c[0] ;
			// Parse state value
			var v = parseFloat(split[4]);
			
			// Parse Timestamp
			var idx = split[1].trim();
			
			//var time = Array.Map(idx.split(":"), function(t) { return +t; });

			var frame = this.index[idx];
			
			if (!frame) {
				frame = new Frame(idx);	
				this.index[idx] = frame;
				this.frames.push(frame);
			}

			frame.AddTransition(model, v);
	
			this.models[model]=model;

		}.bind(this));
	}
}