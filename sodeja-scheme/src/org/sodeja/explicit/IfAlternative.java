package org.sodeja.explicit;

import org.sodeja.runtime.scheme.model.Combination;

class IfAlternative implements Executable {
	@Override
	public String execute(Machine machine) {
		Combination comb = (Combination) machine.exp.getValue();
		
		machine.exp.setValue(comb.get(3));
		
		return "eval-dispatch";
	}
}
