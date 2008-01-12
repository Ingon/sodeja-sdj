package org.sodeja.sil.runtime2;

public class SILPrimitiveObject implements SILObject {

	private SILClass type;
	private Object value;
	
	protected SILPrimitiveObject() {
	}
	
	@Override
	public SILClass getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	protected void setType(SILClass type) {
		this.type = type;
	}
}
