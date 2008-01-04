package org.sodeja.il.sdk;

import java.util.List;

public class ILNotFoundLambda implements ILClassLambda {

	public static final ILSymbol NAME = new ILSymbol("methodMissing");
	
	private final ILSymbol name;
	
	public ILNotFoundLambda(ILSymbol name) {
		this.name = name;
	}
	
	@Override
	public ILObject applyObject(ILObject value, List<ILObject> values) {
		throw new RuntimeException("Method is missing: " + name);
	}

	@Override
	public ILClass getType() {
		throw new UnsupportedOperationException();
	}
}
