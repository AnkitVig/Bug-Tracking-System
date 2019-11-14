'use strict';

import Lang from '../../utils/lang.js';
import Array from '../../utils/array.js';
import Sim from '../../utils/sim.js';
import Frame from '../frame.js';
import Parser from "./parser.js";
import Palette from '../palette.js';
import ChunkReader from '../../components/chunkReader.js';

export default class RISE extends Parser { 
	
	get Type() { return "RISE"; }
	
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
			log : Array.Find(fileList, function(f) { return f.name.match(".log"); })
		}
	}
	
	IsValid() {		
		var d = Lang.Defer();
		
		if (!this.files.log) d.Reject(new Error(`CD++ Parser is not valid for the selected files.` ));
			
		var reader = new ChunkReader();
		
		reader.ReadChunk(this.files.log.raw, 200).then((ev) => {
			var isValid = ev.result.indexOf("0 / L / ") >= 0;
			
			if (isValid) d.Resolve(this);
			
			d.Reject(new Error(`Rise Parser is not valid for the selected files.`));
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
		var start = chunk.indexOf('0 / L / Y', 0);
							
		while (start > -1 && start < chunk.length) {			
			var end = chunk.indexOf('\n', start);
			
			if (end == -1) end = chunk.length + 1;
			
			var length = end - start;
			
			lines.push(chunk.substr(start, length));

			var start = chunk.indexOf('0 / L / Y', start + length);
		}
		
		Array.ForEach(lines, function(line) {
			var split = line.split("/");
			
			// Parse coordinates
			var i = split[4].indexOf('(');
			var j = split[4].indexOf(')');
			var c = split[4].substring(i + 1, j).split(',');
			
			// TODO : Does this ever happen?
			if (c.length < 2) return;

			var model = parseInt(c[1],10) + "-" + parseInt(c[0],10) + "-" + parseInt(c.length==3 ? c[2] : 0, 10);
			
			// Parse state value
			var v = parseFloat(split[6]);
			
			// Parse Timestamp
			var idx = split[3].trim();
			
			var time = Array.Map(idx.split(":"), function(t) { return +t; });

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