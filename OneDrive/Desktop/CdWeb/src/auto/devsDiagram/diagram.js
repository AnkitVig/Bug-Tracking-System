'use strict';

import Lang from '../../utils/lang.js';
import Array from '../../utils/array.js';
import Dom from '../../utils/dom.js';
import Widget from '../../ui/widget.js';

export default Lang.Templatable("Diagram.DevsDiagram", class DevsDiagram extends Widget { 

	constructor(file) {
		super();
		this.dimensions = null;
		this.cell = null;
		this.svgcontent="";
		this.svgcontent=file;

		this.Node("diagram").addEventListener("click", this.onsvgClick_Handler.bind(this));
	
		this.Node("diagram").addEventListener("mousemove", this.onsvgMouseMove_Handler.bind(this));
		this.Node("diagram").addEventListener("mouseout", this.onsvgMouseOut_Handler.bind(this));

	}
	onsvgMouseMove_Handler(ev)
	{
		this.data2="";
		this.data2= ev.target.id;
	
		this.Emit("MouseMove", {x:ev.pageX, y:ev.pageY , data2:this.data2});
	}
		
	onsvgMouseOut_Handler(ev) {		
		this.Emit("MouseOut", { x:ev.pageX, y:ev.pageY });
	}
	
	onsvgClick_Handler(ev) {

		this.selectedid="";
		this.selectedid= ev.target.id;
		
		this.Emit("Click", {x:ev.pageX, y:ev.pageY , selectedid:this.selectedid});
	

	}
	DrawSVGBorder(selected_id,color) {
		// Find the new X, Y coordinates of the clicked cell
	//	console.log(selected_id);
		if(selected_id!="")
		{	

			document.getElementById(selected_id).style.stroke = color;
			document.getElementById(selected_id).style['stroke-width']=5.0;
		}
	}
	Template() {

			   		return "<div class='grid'>" + 
				  "<div handle='title' class='grid-title'>nls(DevsDiagram_Title)</div>" + 
				  "<div handle='diagram-container' class='grid-canvas-container'>" +
					"<div handle='diagram' ></div>" +
					"<div handle='diagram_hidden' hidden></div>" +
				  "</div>" + 
			   "</div>";
	}

	Resize(dimensions) {

		
		if(Array.isArray(dimensions))
		{


		this.dimensions = dimensions;
		this.size = Dom.Geometry(this.Node("diagram-container"));
		
		var pH = (this.dimensions.x) ;
		var pV = (this.dimensions.y);
		
		this.Node("diagram").style.margin = `${pV}px ${pH}px`;		
		// Redefine with and height to fit with number of cells and cell size
		this.Node("diagram").style.width =   `${(this.size.w - (this.dimensions.x ))}px`;	
		this.Node("diagram").style.height =  `${(this.size.h - (this.dimensions.y ))}px`  ;
		}
	else

	{this.size = Dom.Geometry(this.Node("diagram-container"));
		var pH = 30 ;
		var pV = 30;
		this.Node("diagram").style.margin = `${pV}px ${pH}px`;
	this.Node("diagram").style.width =   `${(this.size.w - (30))}px`;	
		this.Node("diagram").style.height =  `${(this.size.h - (30))}px`  ;
			
	}
	}
	
	Draw(state) {
		
			this.DrawState(state);
	
	//	else this.Default(DEFAULT_COLOR);
	}

	DrawState(state)
	{		this.Node('diagram_hidden').innerHTML = this.svgcontent;
			this.Node("diagram_hidden").getElementsByTagName("svg")[0].setAttribute("width", "100%");
			this.Node("diagram_hidden").getElementsByTagName("svg")[0].setAttribute("height", "100%");
			this.Node("diagram_hidden").getElementsByTagName("svg")[0].setAttribute("viewbox", "0 0 560 340"); // as per the cell dimesions 
			this.Node("diagram_hidden").getElementsByTagName("svg")[0].setAttribute("preserveAspectRatio", "none");
			this.Node("diagram_hidden").getElementsByTagName("title").hidden = true;
			
			this.Node('diagram').innerHTML =this.Node('diagram_hidden').innerHTML;
	
}
DrawChanges(state)
{	//TO DRAW INITIAL STATE AFTER SHOWING THE CHANGES .
	this.DrawState(state);

// 		if(frame!=undefined)
 //		{
 	// 		Array.ForEach(frame.transitions, function(t) {
 	 //		
		//	var t_id =t.id;
		//	var v = state.model[t_id];
			
		//	const fill = 'salmon';
		//	if(document.getElementById(t_id)!=null)
		//	document.getElementById(t_id).style.fill=fill;
			

		//}.bind(this));
	//	}
	//	
	//	else
	//	{	
			//var time =this.data.times[state.i];
			var transitions =this.data.transitions[state.i];
			

			Array.ForEach(transitions, function(t) {
 	 		
 	 		const fill = 'LightSeaGreen';
			var t_id =t.id;
			if(document.getElementById(t_id)!=null)
			{document.getElementById(t_id).style.fill=fill;
				const fillstroke = 'teal';

			 document.getElementById(t_id).style.stroke = fillstroke;
			document.getElementById(t_id).style['stroke-width']=5.0;

			}
				
			}.bind(this));
			

	//	}

}

	Data(data) {
		this.data = data;
	}
	

});