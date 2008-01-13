package org.sodeja.sil.runtime;

public class DefaultInstanceSpecification implements InstanceSpecification {
	
	private int namedSize;
	private int indexSize;
	
	public DefaultInstanceSpecification(int namedSize, int indexSize) {
		this.namedSize = namedSize;
		this.indexSize = indexSize;
	}

	@Override
	public SILObject createInstance() {
		return new SILDefaultObject(namedSize, indexSize);
	}
}
