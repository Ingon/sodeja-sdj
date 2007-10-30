package org.sodeja.explicit2;

import org.sodeja.runtime.scheme.model.Symbol;

public class LexicalVariable extends Variable {
	
	private final int lexicalIndex;
	
	public LexicalVariable(Symbol sym, int lexicalIndex) {
		super(sym);
		this.lexicalIndex = lexicalIndex;
	}

	@Override
	public void eval(Machine machine) {
		Enviroment env = machine.env.getValue();
		machine.val.setValue(env.getLexicalValue(lexicalIndex));
		machine.exp.setValue(machine.cont.getValue());
	}
}
