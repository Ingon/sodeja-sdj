package org.sodeja.ilan.runtime;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.ilan.ildk.ILObject;
import org.sodeja.ilan.ildk.ILSymbol;

public class Context {
	
	private Map<ILSymbol, ILObject> values;
	
	public Context() {
		this.values = new HashMap<ILSymbol, ILObject>();
	}

	public ILObject get(ILSymbol symbol) {
		return values.get(symbol);
	}

	public void def(ILSymbol symbol, ILObject value) {
		values.put(symbol, value);
	}
}
