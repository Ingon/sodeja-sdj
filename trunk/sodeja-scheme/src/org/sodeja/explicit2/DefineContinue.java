package org.sodeja.explicit2;

import java.util.List;

import org.sodeja.runtime.scheme.model.Symbol;

public class DefineContinue implements CompiledExpression {
	
	public static final DefineContinue instance = new DefineContinue();
	
	private DefineContinue() {
	}
	
	@Override
	public void eval(Machine machine) {
		machine.cont.restore();
		machine.env.restore();
		machine.unev.restore();
		
		List<CompiledExpression> une = machine.unev.getValue();
		
		Symbol name = ((SymbolCE) une.get(0)).sym;
		Object value = machine.val.getValue();
		
		LexicalEnviroment env = machine.env.getValue();
		if(env != null) {		
			env.define(name, machine.val.getValue());
		} else {
			machine.dynamic.define(name, value);
		}
		
		machine.val.setValue("ok");
		
		machine.exp.setValue(machine.cont.getValue());
	}
}
