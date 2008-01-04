package org.sodeja.il.runtime;

public class ILJavaObject<T> implements ILObject {
	
	protected final ILJavaObjectClass type;
	protected final T value;
	
	public ILJavaObject(ILJavaObjectClass type, T value) {
		this.type = type;
		this.value = value;
	}

	@Override
	public ILClass getType() {
		return type;
	}

	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof ILSymbol)) {
			return false;
		}
		
		return value.equals(((ILJavaObject) obj).value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public String toString() {
		return "<" + value.getClass().getName() + "> " + value;
	}
}
