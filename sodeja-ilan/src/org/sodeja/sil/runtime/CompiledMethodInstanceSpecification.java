package org.sodeja.sil.runtime;

public class CompiledMethodInstanceSpecification implements InstanceSpecification {
	@Override
	public SILObject createInstance() {
		return new SILCompiledMethod();
	}
}
