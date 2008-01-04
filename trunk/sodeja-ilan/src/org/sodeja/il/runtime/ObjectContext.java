package org.sodeja.il.runtime;

import org.sodeja.il.sdk.ILDefaultObject;
import org.sodeja.il.sdk.ILObject;
import org.sodeja.il.sdk.ILSymbol;

public class ObjectContext implements Context {

	private final Context parent;
	private final ILObject obj;
	
	public ObjectContext(Context parent, ILObject obj) {
		this.parent = parent;
		this.obj = obj;
	}

	@Override
	public void define(ILSymbol symbol, ILObject value) {
		if(! symbol.getValue().startsWith("@")) {
			parent.define(symbol, value);
			return;
		}
		
		if(! (obj instanceof ILDefaultObject)) {
			throw new UnsupportedOperationException();
		}
		ILSymbol varSymbol = new ILSymbol(symbol.getValue().substring(1));
		((ILDefaultObject) obj).set(varSymbol, value);
	}

	@Override
	public boolean exists(ILSymbol symbol) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ILObject get(ILSymbol symbol) {
		if(! symbol.getValue().startsWith("@")) {
			return parent.get(symbol);
		}
		
		if(! (obj instanceof ILDefaultObject)) {
			throw new UnsupportedOperationException();
		}
		
		ILSymbol varSymbol = new ILSymbol(symbol.getValue().substring(1));
		return ((ILDefaultObject) obj).get(varSymbol);
	}
}
