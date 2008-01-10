package org.sodeja.sil.runtime;

public class SILPrimitiveObjectContents implements SILObjectContents {
	private Object value;

	public SILPrimitiveObjectContents() {
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
