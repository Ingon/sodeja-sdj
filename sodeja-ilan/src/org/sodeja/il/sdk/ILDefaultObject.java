package org.sodeja.il.sdk;

import java.util.HashMap;
import java.util.Map;

public class ILDefaultObject implements ILObject {

	private final ILClass type;
	private final Map<ILSymbol, ILObject> values;
	
	public ILDefaultObject(ILClass type) {
		this.type = type;
		this.values = new HashMap<ILSymbol, ILObject>();
	}

	@Override
	public ILClass getType() {
		return type;
	}

	public void set(ILSymbol name, ILObject value) {
		values.put(name, value);
	}

	public ILObject get(ILSymbol symbol) {
		return values.get(symbol);
	}

	@Override
	public String toString() {
		return type + "" + values;
	}
}
