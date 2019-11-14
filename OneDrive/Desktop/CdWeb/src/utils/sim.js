'use strict';

import Lang from './lang.js';
import DEVS from '../simulation/parsers/DEVS_parser.js';
import CDpp from '../simulation/parsers/CDpp.js';
import RISE from '../simulation/parsers/RISE.js';
import ChunkReader from '../components/chunkReader.js';

const PARSERS = [DEVS, CDpp, RISE];

export default class Sim {
	
	static DetectParser(fileList) {
		var d = Lang.Defer();
		
		DetectParserI(fileList, 0, d);
		
		return d.promise;
		
		function DetectParserI(fileList, i, d) {
			if (i == PARSERS.length) {
				d.Reject(new Error("Could not detect the simulator used to generate the files"));
				
				return;
			}
			
			var parser = new PARSERS[i]();
			
			var p = parser.Initialize(fileList);
			
			var success = (d, ev) => { d.Resolve({ parser:ev.result }); };
			
			var failure = (fileList, i, d, error) => { DetectParserI(fileList, ++i, d); }
			
			p.then(success.bind(this, d), failure.bind(this, fileList, i, d));
		}
	}
	
	static ReadFile(file, parseFn) {
		var d = Lang.Defer();
		var reader = new ChunkReader();
		
		if (!file) d.Resolve(null);
		
		else {
			reader.Read(file.raw).then((ev) => { 
				file.content = parseFn(ev.result); 
				
				d.Resolve(file.content);
			});
		}
		
		return d.promise;
	}
	
	static RgbToHex(rgb) {
		return "#" + ((1 << 24) + (rgb[0] << 16) + (rgb[1] << 8) + rgb[2]).toString(16).slice(1);
	}
	
	static HexToRgb(hex) {
		var m = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
		
		return m ? [parseInt(m[1], 16), parseInt(m[2], 16), parseInt(m[3], 16)] : null;
	}
}