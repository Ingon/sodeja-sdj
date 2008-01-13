package org.sodeja.sil.runtime;

public class PrimitiveInstanceSpecification implements InstanceSpecification {
	@SuppressWarnings("unchecked")
	@Override
	public SILObject createInstance() {
		return new SILPrimitiveObject();
	}
}
