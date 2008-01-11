package org.sodeja.sil.runtime.memory;

import org.sodeja.sil.runtime.SILObject;

class DelegatingInternalReference extends InternalReference {
	InternalReference delegate;
	
	protected DelegatingInternalReference(ObjectManager manager) {
		super(manager, -1);
	}

	@Override
	public SILObject getValue() {
		return delegate.getValue();
	}
}
