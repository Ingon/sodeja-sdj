package org.sodeja.sil.runtime.memory;

import org.sodeja.sil.runtime.SILObject;

public class InternalReference extends Reference {
	protected InternalReference(ObjectManager manager, Integer id) {
		super(manager, id);
	}

	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof InternalReference)) {
			return false;
		}
		
		return super.equals(obj);
	}
	
	public SILObject getValue() {
		return manager.get(this);
	}
}
