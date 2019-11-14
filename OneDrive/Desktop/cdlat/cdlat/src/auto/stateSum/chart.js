'use strict';

import Lang from '../../utils/lang.js';
import Array from '../../utils/array.js';
import Dom from '../../utils/dom.js';
import Widget from '../../ui/widget.js';
import Chart from '../../components/chart.js';

const BORDER_COLOR = "rgb(100,100,100)";

export default Lang.Templatable("Chart.StateChart", class StateChart extends Widget { 
	
	constructor(id) {
		super(id);
		
		// [top right bottom left]
		this.margin = [20, 0, 40, 60];
		
		this.chart = new Chart(this.Node("chart"));
		
		this.chart.AddGroup("labels");
		this.chart.AddGroup("figures");
		this.chart.AddGroup("axis");
		
		this.AxisY();
		this.AxisX();
	}
	
	AxisY() { 
		this.chart.AddScale("y", d3.scaleLinear());
				
		var yAxis = this.chart.AddAxis("y");
		
		// Label for axis
		var t = this.chart.AddLabel("y", Lang.Nls("StateChart_YAxis"));
		
		t.attr("text-anchor", "end").attr("y", -15);
	}
	
	AxisX() { 	
		var xScale = d3.scaleBand().padding(0.1);
		
		this.chart.AddScale("x", xScale);
		
		var xAxis = this.chart.AddAxis("x");
		
		this.chart.AddLabel("x", Lang.Nls("StateChart_XAxis")).attr("text-anchor", "end");
	}
	
	Resize() {
		var m = this.margin;
		var s = this.chart.Resize();
		
		var h = s.h - m[0] - m[2];
		var w = s.w - m[1] - m[3];
		
		this.chart.Scale("y").rangeRound([h, 0]);
		this.chart.Scale("x").rangeRound([0, w]);
		
		this.chart.Group("figures").attr("transform", `translate(${m[3]}, ${m[0]})`);
		
		this.chart.Axis("y").attr("transform", `translate(${m[3]}, ${m[0]})`);
		this.chart.Axis("x").attr("transform", `translate(${m[3]}, ${s.h - m[2]})`);
		this.chart.Label("x").attr("transform", `translate(${s.w - m[1]}, ${s.h - 10})`); // TODO : +35 should be + height of axis
		this.chart.Label("y").attr("transform", `translate(30, ${m[0]}) rotate(-90)`);  // TODO : +30 should be + width of axis
	}
	
	Draw(yMax) {
		// all this redraws things that can change often
		var domain = Array.Map(this.data, function(d) { return d.label; });
		
		var xScale = this.chart.Scale("x").domain(domain);
		var yScale = this.chart.Scale("y").domain([0, yMax]);
		
		this.chart.Axis("y").call(d3.axisLeft(yScale));
		this.chart.Axis("x").call(d3.axisBottom(xScale));
		
		// draw initial bars
		var bars = this.chart.Group("figures").selectAll(".bar").data(this.data);
		
		var exit = bars.exit().remove();
		
		var enter = bars.enter().append("rect")
					    .attr("class", "bar")
						.on("mouseout", this.onBars_MouseOut.bind(this))
						.on("mousemove", this.onBars_MouseMove.bind(this));
		
		bars.merge(enter)
			.style("fill", function(d, i) { return d.color; })
			.attr("x", function(d) { return xScale(d.label); })
			.attr("width", xScale.bandwidth())
	}
	
	Update() {		
		var y = this.chart.Scale("y");
		var h = this.chart.size.h - this.margin[0] - this.margin[2];

		var bars = this.chart.Group("figures").selectAll(".bar").data(this.data);
		
		bars.attr("y", function(d) { return y(d.total); })
			.attr("height", function(d) { 
				var height = h - y(d.total); 
				
				return height > 0 ? height : 0 ;
			});
	}
	
	Data(data) {
		this.data = data;
	}
			
	onBars_MouseMove(ev) {
		this.Emit("MouseMove", { x:d3.event.pageX, y:d3.event.pageY, data:ev });
	}
	
	onBars_MouseOut(ev) {		
		this.Emit("MouseOut", { x:d3.event.pageX, y:d3.event.pageY });
	}
	
	Template() {
		return "<div class='state chart'>" + 
				  "<div class='chart-title'>nls(StateChart_Title)</div>" + 
				  "<div handle='chart' class='svg-container'></div>" +
			   "</div>";
	}
});