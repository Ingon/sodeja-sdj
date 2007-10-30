package org.sodeja.explicit2;

import java.util.List;

import org.sodeja.runtime.Procedure;
import org.sodeja.runtime.scheme.model.Symbol;

class CompoundProcedure implements Procedure {
	
	protected final Enviroment enviroment;
	protected final List<Symbol> params;
	protected final List<CompiledExpression> body;
	
	public CompoundProcedure(Enviroment enviroment, List<Symbol> params, List<CompiledExpression> body) {
		this.enviroment = enviroment;
		this.params = params;
		this.body = body;
	}

	@Override
	public Object apply(Object... values) {
		throw new UnsupportedOperationException();
	}
}
