import Dom from '../utils/dom.js';
import Lang from '../utils/lang.js';
import Array from '../utils/array.js';
import Templated from '../components/templated.js';

export default class Popup extends Templated { 
	
	set Widget(value) {
		this.widget = value;
		
		this.widget.Place(this.Node("body"));
	}
	
	get Widget() { return this.widget; }
	
	constructor() {	
		super(document.body);
		
		this.widget = null;
		
		this.Node("close").addEventListener("click", this.onBtnClose_Click.bind(this));
		
		this.Hide();
	}
		
	Show() {
		this.Node("root").style.opacity = 1;
		this.Node("root").style.visibility = "visible";
	}
	
	Hide() {
		this.Node("root").style.opacity = 0;
		this.Node("root").style.visibility = "hidden";
	}
	
	onBtnClose_Click(ev) {
		this.Hide();
	}
	
	Template() {
		return "<div handle='root' class='popup'>" +
				  "<div class='popup-container'>" +
					  "<div class='popup-header'>" +
						  "<div class='popup-title' handle='title'></div>" +
						  "<button class='close' handle='close'>×</button>" +
					  "</div>" +
					
					  "<div class='popup-body' handle='body'></div>" +
				  "</div>" +
			  "</div>";
	}
}