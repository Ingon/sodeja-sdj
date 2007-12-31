package org.sodeja.ilan.runtime;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.ilan.ildk.ILObject;
import org.sodeja.ilan.ildk.ILSymbol;

public class ChildContext implements Context {
	
	protected Context parent;
	protected Map<ILSymbol, ILObject> values;
	
	public ChildContext(Context parent) {
		this.values = new HashMap<ILSymbol, ILObject>();
		this.parent = parent;
	}

	public ILObject get(ILSymbol symbol) {
		ILObject object = values.get(symbol);
		if(object == null) {
			return parent.get(symbol);
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

	@Override
	public Process getProcess() {
		return parent.getProcess();
	}
}
