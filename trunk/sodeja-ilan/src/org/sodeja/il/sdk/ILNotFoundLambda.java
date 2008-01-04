package org.sodeja.il.sdk;

import java.util.List;

import org.sodeja.il.runtime.SDK;

public class ILNotFoundLambda implements ILClassLambda {

	public static final ILSymbol NAME = SDK.getInstance().makeSymbol("methodMissing");
	
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
