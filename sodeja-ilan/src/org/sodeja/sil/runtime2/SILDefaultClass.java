package org.sodeja.sil.runtime2;

public class SILDefaultClass implements SILClass {
	private SILObject type;
	private SILObject superclass;
	private SILObject instanceSpecification;
	private SILObject methodDictionary;
	
	protected SILDefaultClass() {
	}
	
	@Override
	public SILObject getType() {
		return type;
	}

	@Override
	public SILObject getSuperclass() {
		return superclass;
	}

	@Override
	public SILObject getInstanceSpecification() {
		return instanceSpecification;
	}

	@Override
	public SILObject getMethodDictionary() {
		return methodDictionary;
	}

	protected void setType(SILObject type) {
		this.type = type;
	}

	protected void setSuperclass(SILObject superclass) {
		this.superclass = superclass;
	}

	protected void setInstanceSpecification(SILObject instanceSpecification) {
		this.instanceSpecification = instanceSpecification;
	}

	protected void setMethodDictionary(SILObject methodDictionary) {
		this.methodDictionary = methodDictionary;
	}
}
