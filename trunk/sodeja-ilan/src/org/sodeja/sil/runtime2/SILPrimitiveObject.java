package org.sodeja.sil.runtime2;

public class SILPrimitiveObject<T> implements SILObject {

	private SILObject type;
	private T value;
	
	protected SILPrimitiveObject() {
	}
	
	@Override
	public SILObject getType() {
		return type;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	protected void setType(SILObject type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
