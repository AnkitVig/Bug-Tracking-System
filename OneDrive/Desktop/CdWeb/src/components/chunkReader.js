'use strict';

import Lang from '../utils/lang.js';
import Array from '../utils/array.js';
import Evented from './evented.js';

const CHUNK_SIZE = 2097152;
const MAX_SIZE = 5500000000;

export default class ChunkReader extends Evented { 
	
	constructor () {
		super();
		
		this.fileReader = new FileReader();
		this.defer = null;
		this.position = 0;
	
		this.fileReader.addEventListener("loadend", this.onLoadEnd_Handler.bind(this));
	}
	
	Read(file) {
		if (this.defer) throw new Error("FileReader is in use");
		
		this.defer = Lang.Defer();
		
		this.fileReader.readAsText(file);
		
		return this.defer.promise;
	}
	
	ReadChunk(file, size) {
		var size = size || CHUNK_SIZE;
		var chunk = file.slice(this.position, this.position + size);
		
		return this.Read(chunk);
	}
	
	MoveCursor(value) {
		this.position += value;
	}

	onLoadEnd_Handler(ev) {
		var resolve = this.defer.Resolve;
		
		this.defer = null;
		
		resolve(this.fileReader.result);
	}
}