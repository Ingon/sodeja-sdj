package org.sodeja.explicit;

import org.sodeja.runtime.scheme.model.Combination;
import org.sodeja.runtime.scheme.model.Symbol;

class Quoted implements Executable {
	@Override
	public String execute(Machine machine) {
		Combination comb = (Combination) machine.exp.getValue();
		return ((Symbol) comb.get(1)).value;
	}
}
