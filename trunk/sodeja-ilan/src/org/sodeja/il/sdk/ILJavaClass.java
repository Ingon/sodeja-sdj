package org.sodeja.il.sdk;

public class ILJavaClass extends ILClass {
	public ILJavaClass(ILSymbol name, ILClass parent) {
		super(name, parent);
	}

	@Override
	public ILObject makeInstance(Object... values) {
		return new ILJavaObject(this, values[0]);
	}
}
