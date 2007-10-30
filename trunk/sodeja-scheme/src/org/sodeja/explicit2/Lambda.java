package org.sodeja.explicit2;

import java.util.List;

import org.sodeja.runtime.scheme.model.Symbol;

class Lambda implements CompiledExpression {
	
	private final List<Symbol> params;
	private final List<CompiledExpression> body;
	
	public Lambda(List<Symbol> params, List<CompiledExpression> body) {
		this.params = params;
		this.body = body;
	}

	@Override
	public void eval(Machine machine) {
		machine.val.setValue(new CompoundProcedure(machine.env.getValue(), params, body));
		
		machine.exp.setValue(machine.cont.getValue());
	}
}
