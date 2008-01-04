package org.sodeja.il.sdk;

public class ILSymbolClass extends ILJavaClass {
	public ILSymbolClass(ILSymbol name, ILClass parent) {
		super(name, parent, String.class);
	}

	@Override
	public ILObject makeInstance(Object... values) {
		return new ILSymbol(this, (String) values[0]);
	}
}
