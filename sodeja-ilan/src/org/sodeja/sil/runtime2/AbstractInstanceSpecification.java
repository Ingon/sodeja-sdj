package org.sodeja.sil.runtime2;

public class AbstractInstanceSpecification implements InstanceSpecification {
	@Override
	public SILObject createInstance() {
		throw new RuntimeException("Instances of this class are not supported");
	}
}
