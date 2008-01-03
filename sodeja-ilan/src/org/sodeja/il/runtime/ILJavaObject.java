package org.sodeja.il.runtime;

public class ILJavaObject<T> extends ILObject {
	
	private final Object value;
	
	public ILJavaObject(ILClass type, Object value) {
		super(type);
		this.value = value;
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
		return ":" + value;
	}
}
