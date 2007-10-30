package org.sodeja.explicit;

import org.sodeja.runtime.scheme.model.Combination;

class Lambda implements Executable {
	@Override
	public String execute(Machine machine) {
		Combination comb = (Combination) machine.exp.getValue();
		
		Combination params = (Combination) comb.get(1);
		Combination body = (Combination) comb.get(2);
		
		machine.val.setValue(new CompoundProcedure(machine.env.getValue(), params, body));
		
		return machine.cont.getValue();
	}
}
