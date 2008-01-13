package org.sodeja.sil.runtime2;

public class CompiledMethodInstanceSpecification implements InstanceSpecification {
	@Override
	public SILObject createInstance() {
		return new SILCompiledMethod();
	}
}
