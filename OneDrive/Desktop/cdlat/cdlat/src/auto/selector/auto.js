'use strict';

import Lang from '../../utils/lang.js';
import Array from '../../utils/array.js';
import Dom from '../../utils/dom.js';
import Tooltip from '../../ui/tooltip.js';
import Selector from './selector.js';
import Automated from '../automated.js';

export default Lang.Templatable("Auto.Selector", class AutoSelector extends Automated { 
	
	constructor(simulation) {
		super(new Selector(), simulation);
		
		this.Widget.On("Load", this.onWidgetLoad_Handler.bind(this));
	}
	
	Refresh() {
		
	}
	
	Destroy() {
		super.Destroy();
		
		this.Widget.Destroy();
	}
	
	Save() {
		return { name:"Auto.Selector" };
	}
	
	onWidgetLoad_Handler(ev) {
		this.Emit("Load", { definition:ev.definition, configurator:ev.configurator  });
	}
});