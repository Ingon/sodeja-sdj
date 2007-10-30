package org.sodeja.explicit;

import org.sodeja.runtime.Procedure;
import org.sodeja.runtime.scheme.model.Combination;

class CompoundProcedure implements Procedure {

	protected final Enviroment enviroment;
	protected final Combination params;
	protected final Combination body;
	
	public CompoundProcedure(Enviroment value, Combination params, Combination body) {
		this.enviroment = value;
		this.params = params;
		this.body = body;
	}

	@Override
	public Object apply(Object... values) {
		throw new UnsupportedOperationException();
	}
}
