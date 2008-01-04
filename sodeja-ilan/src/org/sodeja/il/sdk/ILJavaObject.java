package org.sodeja.il.sdk;

public class ILJavaObject<T> implements ILObject {
	
	protected final ILJavaClass type;
	protected final T value;
	
	public ILJavaObject(ILJavaClass type, T value) {
		this.type = type;
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	@Override
	public ILClass getType() {
		return type;
	}

	@Override
	public String toString() {
		return "<" + value.getClass().getName() + "> " + value;
	}
}
