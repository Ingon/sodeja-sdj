package org.sodeja.il.runtime;

public class ClassContext implements Context {

	private final Context parent;
	private final ILClass type;
	
	public ClassContext(Context parent, ILClass type) {
		this.parent = parent;
		this.type = type;
	}

	@Override
	public void define(ILSymbol name, ILObject value) {
		// TODO maybe class level vars?
		if(! (value instanceof ILClassLambda)) {
			throw new UnsupportedOperationException();
		}
		type.defineLambda(name, (ILClassLambda) value);
	}

	@Override
	public ILObject get(ILSymbol name) {
		ILLambda lambda = type.getLambda(name);
		if(! (lambda instanceof ILNotFoundLambda)) {
			return lambda;
		}
		return parent.get(name);
	}

	@Override
	public boolean exists(ILSymbol symbol) {
		return type.getLambda(symbol) != null;
	}
}
