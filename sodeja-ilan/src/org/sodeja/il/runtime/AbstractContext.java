package org.sodeja.il.runtime;

import java.util.HashMap;
import java.util.Map;

abstract class AbstractContext implements Context {

	private final Map<ILSymbol, ILObject> values;
	
	public AbstractContext() {
		this.values = new HashMap<ILSymbol, ILObject>();
	}

	@Override
	public void define(ILSymbol name, ILObject value) {
		values.put(name, value);
	}

	@Override
	public ILObject get(ILSymbol name) {
		return values.get(name);
	}
}
