'use strict';

import Lang from '../../utils/lang.js';
import Array from '../../utils/array.js';
import Dom from '../../utils/dom.js';
import Widget from '../../ui/widget.js';

const STROKE_WIDTH = 2;
const DEFAULT_COLOR = "#fff";

export default Lang.Templatable("Grid.Grid", class Grid extends Widget { 

	constructor() {
		super();

		this.dimensions = null;
		this.cell = null;
		
		this.ctx = this.Node("canvas").getContext("2d");
		
		this.Node("canvas").addEventListener("mousemove", this.onCanvasMouseMove_Handler.bind(this));
		this.Node("canvas").addEventListener("mouseout", this.onCanvasMouseOut_Handler.bind(this));
		this.Node("canvas").addEventListener("click", this.onCanvasClick_Handler.bind(this));
	}
	
	Template() {
		return "<div class='grid'>" + 
				  "<div handle='title' class='grid-title'>nls(Grid_Title)</div>" + 
				  "<div handle='canvas-container' class='grid-canvas-container'>" +
					"<canvas handle='canvas' class='grid-canvas'></canvas>" +
				  "</div>" + 
			   "</div>";
	}

	Resize(dimensions) {
		this.dimensions = dimensions;
		this.size = Dom.Geometry(this.Node("canvas-container"));

		var cW = Math.floor(this.size.w / this.dimensions.x);
		var cH = Math.floor(this.size.h / this.dimensions.y);
		
		// Find best fit cell size
		this.cell = cW < cH ? cW : cH; 
		
		// Determine offset w, h to center grid as much as possible
		var pH = (this.size.w - (this.dimensions.x * this.cell)) / 2;
		var pV = (this.size.h - (this.dimensions.y * this.cell)) / 2;
		
		this.Node("canvas").style.margin = `${pV}px ${pH}px`;		
		
		// Redefine with and height to fit with number of cells and cell size
		this.Node("canvas").width = this.dimensions.x * this.cell;	
		this.Node("canvas").height = this.dimensions.y * this.cell;			
	}
	
	Draw(state, z, palette, selection) {
		if (this.dimensions) this.DrawState(state, z, palette, selection);
		
		else this.Default(DEFAULT_COLOR);
	}
	
	Clear() {
		this.ctx.clearRect(0, 0, this.size.w, this.size.h);
	}
	
	Default(color) {
		this.ctx.fillStyle = color;
		this.ctx.fillRect(0, 0, this.size.w, this.size.h);
	}
	
	DrawState(state, z, palette, selection) {		
		for (var i = 0; i < this.dimensions.x; i++) {
			for (var j = 0; j < this.dimensions.y; j++) {
				var id = i + "-" + j + "-" + z;
				var v = state.model[id];
							
				var x = i * this.cell;
				var y = j * this.cell;
				
				this.ctx.fillStyle = palette.GetColor(v);
				this.ctx.fillRect(x, y, this.cell, this.cell);
				
				if (selection.IsSelected(i, j, z)) this.DrawCellBorder(i, j, palette.SelectedColor);
			}
		}
	}
	
	DrawChanges(frame, z, palette, selection) {
		Array.ForEach(frame.transitions, function(t) {
			var t_id = t.id.split("-");
			var t_x = t_id[0];
			var t_y = t_id[1];
			var t_z = t_id[2];
			if (t_z != z) return;
			
			var x = t_x * this.cell;
			var y = t_y * this.cell;
			
			this.ctx.fillStyle = palette.GetColor(t.Value);
			this.ctx.fillRect(x, y, this.cell, this.cell);
				
			if (selection.IsSelected(t_x, t_y, z)) this.DrawCellBorder(t_x, t_y, palette.SelectedColor);
		}.bind(this));
	}
	
	GetCell(layerX, layerY) {
		// Find the new X, Y coordinates of the clicked cell
		var pX = layerX - layerX % this.cell;
		var pY = layerY - layerY % this.cell;
		
		var x = pX / this.cell; 
		var y = pY / this.cell; 
		
		return { x:x, y:y };
	}
	
	DeselectCell(x, y, color) {
		this.DrawCell(x, y, color);
		
		
	}
	
	SelectCell(x, y, color) {
		this.DrawCell(x, y, color);
	}
	
	DrawCellBorder(x, y, color) {
		// Find the new X, Y coordinates of the clicked cell
		var pX = x * this.cell;
		var pY = y * this.cell;
		
		var dX = pX + (STROKE_WIDTH / 2);
		var dY = pY + (STROKE_WIDTH / 2);
		var s = this.cell - STROKE_WIDTH;
				
		// Define a stroke style and width
		this.ctx.lineWidth = STROKE_WIDTH;
		this.ctx.strokeStyle = color;
		
		// Draw rectangle, add offset to fix anti-aliasing issue. Subtract from height and width 
		// to draw border internal to the cell
		this.ctx.strokeRect(dX, dY, s, s);
	}
	
	onCanvasClick_Handler(ev) {		
		var data = this.GetCell(ev.layerX, ev.layerY);
		
		this.Emit("Click", { x:ev.pageX, y:ev.pageY, data:data });
	}
	
	onCanvasMouseMove_Handler(ev) {		
		var data = this.GetCell(ev.layerX, ev.layerY);
		
		this.Emit("MouseMove", { x:ev.pageX, y:ev.pageY, data:data });
	}
	
	onCanvasMouseOut_Handler(ev) {		
		this.Emit("MouseOut", { x:ev.pageX, y:ev.pageY });
	}
});