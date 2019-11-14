'use strict';

import Evented from '../components/evented.js';
import Array from '../utils/array.js';

export default class Palette extends Evented { 

	get FPS() { return this.json.fps; }
	set FPS(value) { this.Set("fps", value); }
	
	get Interval() { return 1000 / this.json.fps; }
	
	get RowHeight() { return this.json.rowHeight; }
	set RowHeight(value) { this.Set("rowHeight", value); }
	
	get Loop() { return this.json.loop; }
	set Loop(value) { this.Set("loop", value); }
	
	get ShowGrid() { return this.json.showGrid; }
	set ShowGrid(value) { this.Set("showGrid", value); }
	
	get Cache() { return this.json.cache; }
	set Cache(value) { this.Set("cache", value); }

	constructor() {
		super();
		
		this.json = {
			fps : 10,
			rowHeight : 400,
			loop : false,
			showGrid : false,
			cache : 10
		}
	}
	
	Set(property, value) {
		this.json[property] = value;
		
		this.Emit("Change", { property:property, value:value });
	}
	
	Save() {
		return this.json;
	}
	
	Load(config) {
		this.json = {
			fps : config.fps,
			rowHeight : config.rowHeight,
			loop : config.loop,
			showGrid : config.showGrid,
			cache : config.cache
		}
		
		this.Emit("Session", { settings:this });
	}
}