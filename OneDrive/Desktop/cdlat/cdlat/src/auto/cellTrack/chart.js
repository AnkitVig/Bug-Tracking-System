'use strict';

import Lang from '../../utils/lang.js';
import Array from '../../utils/array.js';
import Dom from '../../utils/dom.js';
import Widget from '../../ui/widget.js';
import Chart from '../../components/chart.js';

export default Lang.Templatable("Chart.CellTrackChart", class CellTrackChart extends Widget { 

	constructor() {
		super();
		
		// [top right bottom left]
		this.margin = [20, 0, 40, 60];
		
		this.chart = new Chart(this.Node("chart"));
		
		this.chart.AddGroup("labels");
		this.chart.AddGroup("axis");
		this.chart.AddGroup("figures");
		
		this.AxisY();
		this.AxisX();
		
		// Vertical line
		var o = this.chart.AddGroup("overlay");
		
		this.vertical = o.append("line")
						 .classed('overlay-vertical-line', true)
						 .style("fill", "none")
						 .style("pointer-events", "none")
						 .style("display", "none");
		
		this.overlay = o.append("rect")
						.attr("fill", "transparent")
						.on("mouseout", this.onOverlay_MouseOut.bind(this))
						.on("mousemove", this.onOverlay_MouseMove.bind(this));
	}
	
	AxisY() { 
		this.chart.AddScale("y", d3.scaleLinear());
				
		var yAxis = this.chart.AddAxis("y");
		
		var t = this.chart.AddLabel("y", Lang.Nls("CellStateChart_YAxis"));
		
		t.attr("text-anchor", "end").attr("y", -15);
	}
	
	AxisX() { 			
		this.chart.AddScale("x", d3.scaleLinear());
		
		var xAxis = this.chart.AddAxis("x");
		
		this.chart.AddLabel("x", Lang.Nls("CellStateChart_XAxis")).attr("text-anchor", "end");
	}
	
	Resize() {
		var m = this.margin;
		var s = this.chart.Resize();
		
		var h = s.h - m[0] - m[2];
		var w = s.w - m[1] - m[3];
		
		this.chart.Scale("y").rangeRound([h, 0]);
		this.chart.Scale("x").rangeRound([0, w]);
		
		this.chart.Group("figures").attr("transform", `translate(${m[3]}, ${m[0]})`);
		this.chart.Group("overlay").attr("transform", `translate(${m[3]}, ${m[0]})`);
		
		this.chart.Axis("y").attr("transform", `translate(${m[3]}, ${m[0]})`);
		this.chart.Axis("x").attr("transform", `translate(${m[3]}, ${s.h - m[2]})`);
		this.chart.Label("x").attr("transform", `translate(${s.w - m[1]}, ${s.h - 10})`); // TODO : +35 should be + height of axis
		this.chart.Label("y").attr("transform", `translate(30, ${m[0]}) rotate(-90)`);  // TODO : +30 should be + width of axis
	
		this.vertical.attr('y1', 0).attr('y2', h);
		this.overlay.attr("width", w).attr("height", h);
	}
	
	Draw() {
		
		// all this redraws things that can change often
		var domain = Array.Map(this.data, function(d) { return d.label; });
		
		var xScale = this.chart.Scale("x").domain([0, this.data.times.length]);
		var yScale = this.chart.Scale("y").domain([this.data.min, this.data.max]);
		
		this.chart.Axis("y").call(d3.axisLeft(yScale));
		this.chart.Axis("x").call(d3.axisBottom(xScale));
		
		// draw initial bars
		var lines = this.chart.Group("figures").selectAll(".line").data(this.data.series, (s) => { return s.id; });
		
		var exit = lines.exit().remove();
		
		var enter = lines.enter().append("path")
						 .attr("fill", "none");
	
		var curve = this.data.type == "discrete" ? d3.curveStep : d3.curveLinear;
	
		var line = d3.line()
                     .curve(curve)
					 .x(function(d, i) { return xScale(i); })
					 .y(function(d, i) { return yScale(d); });
		
		lines.merge(enter).attr("d", function(d) { return line(d.values); })
					      .attr("class", function(d, i) { return `line line-${i + 1}`; });
		
		var mice = this.chart.Group("overlay").selectAll(".mouse").data(this.data.series, (s) => { return s.id; });
		
		var exit = mice.exit().remove();
		
		var enter = mice.enter().append("circle")
						.attr("r", 5)
						.attr("fill", "none")
					    .style("opacity", "0")
						.style("pointer-events", "none");
						
		mice.merge(enter).attr("class", function(d, i) { return `mouse line-${i + 1}`; });
	}
	
	Update(i) {
		var x = this.chart.Scale("x")(i);	

		this.vertical.attr('x1', x).attr('x2', x).style('display', i > 0 ? null : "none");
	}
			
	Data(data) {
		this.data = data;
	}
			
	onOverlay_MouseMove(ev) {
		var x = this.chart.Scale("x");
		var y = this.chart.Scale("y");
		
		var mouse = d3.mouse(this.overlay.node());		
		var pos = { x:mouse[0], y:null };	
		var data = { x:Math.floor(x.invert(pos.x)), y:[] }
		
		var circles = this.chart.Group("overlay").selectAll(".mouse").style("opacity", "1");
        
		circles.attr("transform", function(d, i) {
			var y0 = d.values[data.x];
			
			data.y.push({ id:d.id, y:y0 });
			
			pos.y = y(y0);
			
			return `translate(${pos.x}, ${pos.y})`;
		})
		  
		this.Emit("MouseMove", { x:d3.event.pageX, y:d3.event.pageY, data:data });
	}
	
	onOverlay_MouseOut(ev) {
		this.chart.Group("overlay").selectAll(".mouse").style("opacity", "0");
		
		this.Emit("MouseOut", { x:d3.event.pageX, y:d3.event.pageY });
	}
	
	Template() {
		return "<div class='cell-track chart'>" + 
				  "<div class='chart-title'>nls(CellTrackChart_Title)</div>" + 
				  "<div handle='chart' class='svg-container'></div>" +
			   "</div>";
	}
});