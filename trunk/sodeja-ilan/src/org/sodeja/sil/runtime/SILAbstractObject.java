package org.sodeja.sil.runtime;

public class SILAbstractObject implements SILObject {

	private final SILClass type;
	
	public SILAbstractObject(SILClass type) {
		this.type = type;
	}
	
	@Override
	public SILClass getType() {
		return type;
	}

}
