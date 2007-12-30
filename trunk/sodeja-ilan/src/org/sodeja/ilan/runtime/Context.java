package org.sodeja.ilan.runtime;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.ilan.ildk.ILObject;
import org.sodeja.ilan.ildk.ILSymbol;

public class Context {
	
	private Context parent;
	private Map<ILSymbol, ILObject> values;
	
	public Context() {
		this.values = new HashMap<ILSymbol, ILObject>();
	}

	public Context(Context parent) {
		this();
		this.parent = parent;
	}

	public ILObject get(ILSymbol symbol) {
		ILObject object = values.get(symbol);
		if(object == null) {
			if(parent != null) {
				return parent.get(symbol);
			}
			throw new RuntimeException("Unknown value: " + symbol);
		}
		return object;
	}

	public void def(ILSymbol symbol, ILObject value) {
		ILObject object = values.get(symbol);
		if(object != null) {
			throw new RuntimeException("Cannot override definition");
		}
		values.put(symbol, value);
	}
}
