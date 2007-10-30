package org.sodeja.explicit;

import org.sodeja.runtime.scheme.model.Symbol;

class Variable implements Executable {
	@Override
	public String execute(Machine machine) {
		Enviroment env = machine.env.getValue();
		Symbol sym = (Symbol) machine.exp.getValue();
		
		machine.val.setValue(env.lookup(sym));
		return machine.cont.getValue();
	}
}
