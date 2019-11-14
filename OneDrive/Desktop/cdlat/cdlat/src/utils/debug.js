'use strict';

export default class Debug {
	static CompareGridStates(f1, f2) {
		for (var i = 0; i < f1.length; i++) {
			for (var j = 0; j < f1.length; j++) {
				for (var k = 0; k < f1.length; k++) {
					if (f1[i][j][k] != f2[i][j][k]) return false;
				} 
			} 
		} 
	
		return true;
	}
};