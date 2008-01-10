package org.sodeja.sil.runtime;

public class SILObject {
	private final SILClass type;
	
	public SILObject(SILClass type) {
		this.type = type;
	}

	public SILClass getType() {
		return type;
	}
}
