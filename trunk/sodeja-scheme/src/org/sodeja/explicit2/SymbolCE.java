package org.sodeja.explicit2;

import org.sodeja.runtime.scheme.model.Symbol;

class SymbolCE implements CompiledExpression {
	final Symbol sym;

	public SymbolCE(Symbol sym) {
		this.sym = sym;
	}

	@Override
	public void eval(Machine machine) {
		throw new UnsupportedOperationException();
	}
}