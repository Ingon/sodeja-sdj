package org.sodeja.explicit2;

import org.sodeja.collections.ListUtils;
import org.sodeja.runtime.scheme.model.Symbol;

class Define implements CompiledExpression {

	private final Symbol name;
	private final CompiledExpression value;
	
	public Define(Symbol name, CompiledExpression value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public void eval(Machine machine) {
		machine.unev.setValue(ListUtils.asList((CompiledExpression) new SymbolCE(name)));
		machine.exp.setValue(value);
		
		machine.unev.save();
		machine.env.save();
		machine.cont.save();
		
		machine.cont.setValue(DefineContinue.instance);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[define ");
		sb.append(name);
		sb.append(" ");
		sb.append(value);
		sb.append("]");
		return sb.toString();
	}
}
