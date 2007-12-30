package org.sodeja.ilan.runtime;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.ilan.ildk.ILObject;
import org.sodeja.ilan.ildk.ILSymbol;

public class IContext {
	
	private Process process;
	private IContext parent;
	private Map<ILSymbol, ILObject> values;
	
	public IContext() {
		this.values = new HashMap<ILSymbol, ILObject>();
	}

	public IContext(IContext parent) {
		this.values = new HashMap<ILSymbol, ILObject>();
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
