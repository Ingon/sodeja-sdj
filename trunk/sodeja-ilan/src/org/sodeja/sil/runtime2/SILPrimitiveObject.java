package org.sodeja.sil.runtime2;

public class SILPrimitiveObject<T> implements SILObject {

	private SILClass type;
	private T value;
	
	protected SILPrimitiveObject() {
	}
	
	@Override
	public SILClass getType() {
		return type;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	protected void setType(SILClass type) {
		this.type = type;
	}
}
