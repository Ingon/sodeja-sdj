package org.sodeja.explicit2;

import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.runtime.Procedure;
import org.sodeja.runtime.scheme.model.Symbol;

class CompoundProcedure implements Procedure {
	
	protected final Enviroment enviroment;
	protected final List<Symbol> params;
	protected final ConsList<CompiledExpression> body;
	
	public CompoundProcedure(Enviroment enviroment, List<Symbol> params, List<CompiledExpression> body) {
		this.enviroment = enviroment;
		this.params = params;
		this.body = ConsList.createList(body);
	}

	@Override
	public Object apply(Object... values) {
		throw new UnsupportedOperationException();
	}
}
