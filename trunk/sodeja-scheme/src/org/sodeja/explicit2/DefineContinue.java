package org.sodeja.explicit2;

import java.util.List;

public class DefineContinue implements CompiledExpression {
	
	public static final DefineContinue instance = new DefineContinue();
	
	private DefineContinue() {
	}
	
	@Override
	public void eval(Machine machine) {
		machine.cont.restore();
		machine.env.restore();
		machine.unev.restore();
		
		Enviroment env = machine.env.getValue();
		
		List<CompiledExpression> une = machine.unev.getValue();
		env.define(((SymbolCE) une.get(0)).sym, machine.val.getValue());
		
		machine.val.setValue("ok");
		
		machine.exp.setValue(machine.cont.getValue());
	}
}
