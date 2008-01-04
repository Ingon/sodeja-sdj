package org.sodeja.il.runtime;

public class ObjectContext implements Context {

	private final Context parent;
	private final ILObject obj;
	
	public ObjectContext(Context parent, ILObject obj) {
		this.parent = parent;
		this.obj = obj;
	}

	@Override
	public void define(ILSymbol symbol, ILObject value) {
		if(! symbol.value.startsWith("@")) {
			parent.define(symbol, value);
			return;
		}
		
		if(! (obj instanceof ILDefaultObject)) {
			throw new UnsupportedOperationException();
		}
		ILSymbol varSymbol = SDK.getInstance().makeSymbol(symbol.value.substring(1));
		((ILDefaultObject) obj).set(varSymbol, value);
	}

	@Override
	public boolean exists(ILSymbol symbol) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ILObject get(ILSymbol symbol) {
		if(! symbol.value.startsWith("@")) {
			return parent.get(symbol);
		}
		
		if(! (obj instanceof ILDefaultObject)) {
			throw new UnsupportedOperationException();
		}
		
		ILSymbol varSymbol = SDK.getInstance().makeSymbol(symbol.value.substring(1));
		return ((ILDefaultObject) obj).get(varSymbol);
	}
}
