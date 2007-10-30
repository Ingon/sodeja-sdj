package org.sodeja.explicit;

import org.sodeja.runtime.scheme.model.Combination;

class Begin implements Executable {
	@Override
	public String execute(Machine machine) {
		Combination comb = (Combination) machine.exp.getValue();
		machine.unev.setValue(comb.subList(1, comb.size()));
		
		machine.cont.save();
		
		return "ev-sequence";
	}
}
