'use strict';


export default class _Array {

	static Find(array, delegate) {
		if (!array) return null;
				
		for (var i=0; i < array.length; i++) {
			if (delegate(array[i], i)) return array[i];
		}
		
		return null;
	}
	
	static Filter(array, delegate) {
		if (!array) return null;
				
		var filtered = [];
		
		for (var i=0; i < array.length; i++) {
			if (delegate(array[i])) filtered.push(array[i]);
		}
		
		return filtered;	
	}
	
	static FindIndex(array, delegate) {
		if (!array) return null;
		
		for (var i=0; i < array.length; i++) {
			if (delegate(array[i])) return i;
		}
		
		return null;
	}
	
	static Has(array, delegate) {
		if (!array) return false;
		
		for (var i in array) {
			if (delegate(array[i], i)) return true;
		}
		
		return false;
	}
	
	static Contains(array, value) {
		if (!array) return false;
		
		for (var i=0; i < array.length; i++) { 
			if (array[i] === value) return true;
		}
		
		return false;
	}
	
	static ForEach(array, delegate) {
		for (var i=0; i < array.length; i++) { 
			delegate(array[i], i);
		}
	}
	
	static Map(array, delegate) {
		var arr = [];
		
		for (var i=0; i < array.length; i++) { 
			arr.push(delegate(array[i], i));
		}
		
		return arr;
	}
	
	static Join(array, separator, delegate) {
		var s = [];
		
		this.ForEach(array, function(item) {
			s.push(delegate(item));
		});
		
		return s.join(separator);
	}
	
	static Sort(array, delegate, desc) {
		return array.sort((!!desc) ? descending : ascending);
		
		function ascending(a, b) {
			if (delegate(a) == null && delegate(b) == null) return 0;
			if (delegate(a) == null) return 1;
			if (delegate(b) == null) return -1;
	
			if (delegate(a) < delegate(b)) return -1;
			if (delegate(a) > delegate(b)) return 1;
			
			return 0; 
		}
		
		function descending(a, b) {
			if (delegate(a) == null && delegate(b) == null) return 0;
			if (delegate(a) == null) return 1;
			if (delegate(b) == null) return -1;
			
			if (delegate(a) > delegate(b)) return -1;
			if (delegate(a) < delegate(b)) return 1;
			
			return 0; 
		}
	}
	
	static Convert(array, delegate) {
		for (var i in array) {
			array[i] = delegate(array[i], i);
		}
	}
	
	static Index(array, delegate) {
		var index = {};
		
		for (var i in array) { 
			var key = delegate(array[i]);
			
			index[key] = array[i];
		}
		
		return index;
	}
	
	static UniqueConcat(a, b) {
		return a.concat(b.filter(function (item) {
			return a.indexOf(item) < 0;
		}));
	}
	
	static isArray(arr) {
		return Array.isArray(arr);
	}
	
	static Clone(array) {
		var clone = [];
		
		for (var i = 0; i < array.length; i++) {
			if (this.isArray(array[i])) clone.push(this.Clone(array[i]));
			
			else clone.push(array[i]);
		}
		
		return clone;
	}
}