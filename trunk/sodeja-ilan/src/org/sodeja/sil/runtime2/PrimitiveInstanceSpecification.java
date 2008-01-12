package org.sodeja.sil.runtime2;

public class PrimitiveInstanceSpecification implements InstanceSpecification {
	@SuppressWarnings("unchecked")
	@Override
	public SILObject createInstance() {
		return new SILPrimitiveObject();
	}
}
