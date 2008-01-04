package org.sodeja.il.runtime;

public class ILSymbolClass extends ILJavaObjectClass {
	public ILSymbolClass(ILSymbol name, ILClass parent) {
		super(name, parent);
	}

	@Override
	public ILObject makeInstance(Object... values) {
		return new ILSymbol(this, (String) values[0]);
	}
}
