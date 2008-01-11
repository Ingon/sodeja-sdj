package org.sodeja.sil.runtime.memory;

public class InternalReference extends Reference {
	protected InternalReference(Integer id) {
		super(id);
	}

	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof InternalReference)) {
			return false;
		}
		
		return super.equals(obj);
	}
}
