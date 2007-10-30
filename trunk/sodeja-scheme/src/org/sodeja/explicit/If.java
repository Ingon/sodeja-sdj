package org.sodeja.explicit;

import org.sodeja.runtime.scheme.model.Combination;

class If implements Executable {
	@Override
	public String execute(Machine machine) {
		machine.exp.save();
		machine.env.save();
		machine.cont.save();
		
		Combination comb = (Combination) machine.exp.getValue();
		machine.cont.setValue("ev-if-decide");
		machine.exp.setValue(comb.get(1));
		
		return "eval-dispatch";
	}
}
