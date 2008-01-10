package org.sodeja.sil.runtime;

public class SILJavaObject<T> extends SILObject {
	private T value;
	
	public SILJavaObject(SILClass type) {
		super(type);
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
}
