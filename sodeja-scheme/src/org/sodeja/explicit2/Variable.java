package org.sodeja.explicit2;

import org.sodeja.runtime.scheme.model.Symbol;

class Variable implements CompiledExpression {

	private final Symbol sym;
	
	public Variable(Symbol sym) {
		this.sym = sym;
	}

	@Override
	public void eval(Machine machine) {
		Enviroment env = machine.env.getValue();
		machine.val.setValue(env.lookup(sym));
		machine.exp.setValue(machine.cont.getValue());
	}
}
