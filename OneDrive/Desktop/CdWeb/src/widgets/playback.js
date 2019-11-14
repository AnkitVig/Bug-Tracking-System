'use strict';

import Lang from '../utils/lang.js';
import Dom from '../utils/dom.js';
import Widget from '../ui/widget.js';

export default Lang.Templatable("Widget.Playback", class Playback extends Widget { 

	constructor(id) {
		super(id);
		
		this.current = 0;
		this.interval = null;
		this.direction = null;
		
		this.Enable(false);
		
		this.Node("first").addEventListener("click", this.onFirstClick_Handler.bind(this));
		this.Node("stepBack").addEventListener("click", this.onStepBackClick_Handler.bind(this));
		this.Node("rewind").addEventListener("click", this.onRewindClick_Handler.bind(this));
		this.Node("play").addEventListener("click", this.onPlayClick_Handler.bind(this));
		this.Node("stepForward").addEventListener("click", this.onStepForwardClick_Handler.bind(this));
		this.Node("last").addEventListener("click", this.onLastClick_Handler.bind(this));
		this.Node("slider").addEventListener("input", this.onSliderChange_Handler.bind(this));
	}
	
	Initialize(simulation, settings) {
		this.simulation = simulation;
		this.settings = settings;
		
		this.simulation.On("Session", this.onSimulationSession_Handler.bind(this));
		
		this.values = this.simulation.frames.map((f) => { return f.time; });
		
		this.min = 0;
		this.max = this.values.length - 1;
		
		this.Node("slider").setAttribute("min", this.min);
		this.Node("slider").setAttribute("max", this.max);
		
		this.SetCurrent(this.min);
		
		this.Enable(true);
	}
	
	Enable (isEnabled) {
		this.Node("first").disabled = !isEnabled;
		this.Node("stepBack").disabled = !isEnabled;
		this.Node("rewind").disabled = !isEnabled;
		this.Node("play").disabled = !isEnabled;
		this.Node("stepForward").disabled = !isEnabled;
		this.Node("last").disabled = !isEnabled;
		this.Node("slider").disabled = !isEnabled;
	}
	
	SetCurrent(i) {
		this.current = i;
		
		this.Node("label").innerHTML = this.values[this.current];
		this.Node("slider").value = this.current;
	}
	
	Stop() {
		var d = this.direction;
		
		this.direction = null;
		
		if (this.interval) clearInterval(this.interval);
		
		Dom.SetCss(this.Node("rewind"), "fas fa-backward");
		Dom.SetCss(this.Node("play"), "fas fa-play");
		
		return d;
	}
	
	GoToPrevious() {
		this.SetCurrent(--this.current);
		
		this.simulation.GoToPreviousFrame();
	}
	
	GoToNext() {
		this.SetCurrent(++this.current);
		
		this.simulation.GoToNextFrame();
	}
	
	GoTo(i) {
		this.SetCurrent(i);
		
		this.simulation.GoToFrame(i);
	}
	
	onFirstClick_Handler(ev) {
		this.Stop();
		
		this.GoTo(this.min);
	}
	
	onStepBackClick_Handler(ev) {
		this.Stop();
		
		if (this.current > this.min) this.GoToPrevious();
		
		else if (this.settings.Loop) this.GoTo(this.max);
	}
	
	onRewindClick_Handler(ev) {
		if (this.Stop() == "rewind") return;
		
		this.direction = "rewind";
		
		Dom.SetCss(this.Node("rewind"), "fas fa-pause");
		
		this.interval = setInterval(function(){ 
			if (this.current > this.min) this.GoToPrevious();
		
			else if (this.settings.Loop) this.GoTo(this.max);
			
			else this.Stop();
		}.bind(this), this.settings.Interval);
	}
	
	onPlayClick_Handler(ev) {
		if (this.Stop() == "play") return;
		
		this.direction = "play";
		
		Dom.SetCss(this.Node("play"), "fas fa-pause");
		
		this.interval = setInterval(function(){ 
			if (this.current < this.max) this.GoToNext();
		
			else if (this.settings.Loop) this.GoTo(this.min);
			
			else this.Stop();
		}.bind(this), this.settings.Interval);
	}
	
	onStepForwardClick_Handler(ev) {
		this.Stop();
		
		if (this.current < this.max) this.GoToNext();
		
		else if (this.settings.Loop) this.GoTo(this.min)
	}
	
	onLastClick_Handler(ev) {
		this.Stop();
		
		this.GoTo(this.max);
	}
	
	onSliderChange_Handler(ev) {
		this.Stop();
		
		this.GoTo(+ev.target.value);
	}
	
	onSimulationSession_Handler(ev) {
		this.SetCurrent(this.simulation.State.i);
	}
	
	Template() {
		return "<div class='controls'>" +
				  "<button handle='first' title='nls(Playback_FastBackward)' class='fas fa-fast-backward'></button>" +
				  "<button handle='stepBack' title='nls(Playback_StepBack)' class='fas fa-step-backward'></button>" +
				  "<button handle='rewind' title='nls(Playback_Backwards)' class='fas fa-backward'></button>" +
				  "<button handle='play' title='nls(Playback_Play)' class='fas fa-play'></button>" +
				  "<button handle='stepForward' title='nls(Playback_StepForward)' class='fas fa-step-forward'></button>" +
				  "<button handle='last' title='nls(Playback_FastForward)' class='fas fa-fast-forward'></button>" +
			   "</div>" + 
			   "<input handle='slider' class='slider' title='nls(Playback_Seek)' type='range' min='0' max='1'>" +
			   "<label handle='label' class='label'></label>";
	}
});