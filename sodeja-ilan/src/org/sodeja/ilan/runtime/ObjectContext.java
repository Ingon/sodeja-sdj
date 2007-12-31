package org.sodeja.ilan.runtime;

import org.sodeja.ilan.ildk.ILObject;
import org.sodeja.ilan.ildk.ILSymbol;

public class ObjectContext extends ChildContext {
	public ObjectContext(Context parent) {
		super(parent);
	}

	@Override
	public void def(ILSymbol symbol, ILObject value) {
		if(symbol.value.startsWith("@")) {
			super.def(symbol, value);
			return;
		}
		parent.def(symbol, value);
	}

	@Override
	public ILObject get(ILSymbol symbol) {
		if(symbol.value.startsWith("@")) {
			return super.get(symbol);
		}
		return parent.get(symbol);
	}
}
