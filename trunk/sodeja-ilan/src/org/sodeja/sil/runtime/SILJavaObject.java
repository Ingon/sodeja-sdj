package org.sodeja.sil.runtime;

public class SILJavaObject<T> extends SILObject {
	private T value;
	
	public SILJavaObject(Reference type) {
		super(type);
	}

	public SILJavaObject(Reference type, T value) {
		super(type);
		setValue(value);
	}
	
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
}
