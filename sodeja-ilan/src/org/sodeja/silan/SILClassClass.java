package org.sodeja.silan;

public class SILClassClass extends SILClass {
	public SILClassClass(SILClass superclass) {
		super(superclass);
	}

	private SILClass instanceClass;

	public SILClass getInstanceClass() {
		return instanceClass;
	}

	protected void setInstanceClass(SILClass instanceClass) {
		if(this.instanceClass != null) {
			throw new RuntimeException("Tring to override the instance class field");
		}
		this.instanceClass = instanceClass;
	}
}
