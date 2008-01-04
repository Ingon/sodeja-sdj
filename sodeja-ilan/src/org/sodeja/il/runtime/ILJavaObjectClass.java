package org.sodeja.il.runtime;

public class ILJavaObjectClass extends ILClass {
	public ILJavaObjectClass(ILSymbol name, ILClass parent) {
		super(name, parent);
	}

	@Override
	public ILObject makeInstance(Object... values) {
		return new ILJavaObject(this, values[0]);
	}
}
