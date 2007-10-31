package org.sodeja.explicit2;

import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.runtime.Procedure;
import org.sodeja.runtime.scheme.model.Symbol;

class CompoundProcedure implements Procedure {
	
	protected final DynamicEnviroment dynamic;
	protected final LexicalEnviroment lexical;
	
	protected final List<Symbol> params;
	protected final ConsList<CompiledExpression> body;
	
	public CompoundProcedure(DynamicEnviroment dynamic, List<Symbol> params, List<CompiledExpression> body) {
		this.dynamic = dynamic;
		this.lexical = null;
		
		this.params = params;
		this.body = ConsList.createList(body);
	}

	public CompoundProcedure(LexicalEnviroment lexical, List<Symbol> params, List<CompiledExpression> body) {
		this.dynamic = null;
		this.lexical = lexical;
		
		this.params = params;
		this.body = ConsList.createList(body);
	}
	
	@Override
	public Object apply(Object... values) {
		throw new UnsupportedOperationException();
	}
}
