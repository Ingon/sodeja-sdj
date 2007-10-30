package org.sodeja.explicit;

import org.sodeja.math.Rational;
import org.sodeja.runtime.scheme.model.Symbol;

class Self implements Executable {
	@Override
	public String execute(Machine machine) {
		Symbol sym = (Symbol) machine.exp.getValue();
//		machine.val.setValue(new Integer(sym.value));
		machine.val.setValue(new Rational(sym.value));
		return machine.cont.getValue();
	}
}
