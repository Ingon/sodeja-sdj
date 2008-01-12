package org.sodeja.sil.runtime2;

public class ClassInstanceSpecification implements InstanceSpecification {
	@Override
	public SILObject createInstance() {
		return new SILClass();
	}
}
