package org.sodeja.sil.runtime2;

public class SILClass extends SILDefaultObject {
	protected SILClass() {
		super(3, 0);
	}
	
	public SILObject getSuperclass() {
		return namedVars[0];
	}

	public SILObject getInstanceSpecification() {
		return namedVars[1];
	}

	public SILObject getMethodDictionary() {
		return namedVars[2];
	}

	protected void setSuperclass(SILObject superclass) {
		namedVars[0] = superclass;
	}

	protected void setInstanceSpecification(SILObject instanceSpecification) {
		namedVars[1] = instanceSpecification;
	}

	protected void setMethodDictionary(SILObject methodDictionary) {
		namedVars[2] = methodDictionary;
	}
}
