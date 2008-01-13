package org.sodeja.sil.runtime;

public class ClassInstanceSpecification implements InstanceSpecification {
	@Override
	public SILObject createInstance() {
		return new SILClass();
	}
}
