package org.sodeja.il.runtime;

public class RootContext extends AbstractContext {
	
	public RootContext() {
	}
	
	@Override
	public void define(ILSymbol name, ILObject value) {
		ILObject tvalue = super.get(name);
		if(tvalue != null) {
			throw new RuntimeException("Redefine is not possible");
		}
		super.define(name, value);
	}

	@Override
	public ILObject get(ILSymbol name) {
		ILObject temp = super.get(name);
		if(temp == null) {
			throw new RuntimeException("Not defined: " + name);
		}
		return temp;
	}
}
