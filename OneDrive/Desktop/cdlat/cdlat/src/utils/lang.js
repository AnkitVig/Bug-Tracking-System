'use strict';
	
let _nls = null;
let _locale = null;
let _templatables = {}

export default class Lang {
			
    static get nls() { return _nls; }
	
    static set nls(value) { _nls = value; }
			
    static get locale() { return _locale; }
	
    static set locale(value) { _locale = value; }
	
	static Nls(id, subs, locale) {
		if (!this.nls) throw new Error("Nls content not set.");
		
		var itm = this.nls[id];

		if (!itm) throw new Error("Nls String '" + id + "' undefined.");

		var txt = itm[(locale) ? locale : this.locale];

		if (txt === undefined || txt === null) throw new Error("String does not exist for requested language.");

		return this.Format(txt, subs);
	}
	
	static Mixin(a, b) {				
		for (var key in b) {
			if (b.hasOwnProperty(key)) a[key] = b[key];
		}

		return arguments[0];
	}

	static GetUrlParameter (name) {				
		name = name.replace(/[\[\]]/g, '\\$&');
		
		var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)');
		
		results = regex.exec(window.location.href);
		
		if (!results) return null;
		
		if (!results[2]) return '';
		
		return decodeURIComponent(results[2].replace(/\+/g, ' '));
	}

	// debouncing function from John Hann, slightly modified 
	// http://unscriptable.com/index.php/2009/03/20/debouncing-javascript-methods/
	static Debounce(func, threshold) {
		var timeout;
	
		return function debounced () {
			
			function delayed () {
				func.apply(this, arguments);
				
				timeout = null; 
			}
	 
			if (timeout) clearTimeout(timeout);
	 
			timeout = setTimeout(delayed.bind(this), threshold || 100); 
		};
	}
	
	static Format(str, subs) {
		if (!subs || subs.length == 0) return str;
		
		var s = str;

		for (var i = 0; i < subs.length; i++) {
			var reg = new RegExp("\\{" + i + "\\}", "gm");
			s = s.replace(reg, subs[i]);
		}

		return s;
	}
	
	static Defer() {
		var defer = {};
		
		defer.promise = new Promise((resolve, reject) => {
			defer.Resolve = (result) => { resolve({ result:result }); };
			defer.Reject = (error) => { reject({ error:error }); };
		});
		
		return defer;
	}
	
	static Templatable(id, definition) {
		if (definition) {
			if (_templatables[id]) throw new Error(`Templatable ${id} is defined multiple times.`);
			
			else _templatables[id] = definition;
		}
		else if (!_templatables[id]) throw new Error(`Templatable ${id} is not defined.`);
		
		return _templatables[id];
	}
	
	static Widgets(namespace) {
		var widgets = [];
		
		for (var id in _templatables) {
			if (id.match(namespace)) widgets.push(_templatables[id]);
		}
		
		return widgets;
	}
	
	static Download(name, content) {
		var link = document.createElement("a");
		
		link.href = "data:application/octet-stream," + encodeURIComponent(content);
		link.download = name;
		link.click();
		link = null;
	}
}