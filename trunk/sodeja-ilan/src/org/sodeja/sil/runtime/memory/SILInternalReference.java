package org.sodeja.sil.runtime.memory;

public class SILInternalReference extends SILReference {
	protected SILInternalReference(Integer id) {
		super(id);
	}

	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof SILInternalReference)) {
			return false;
		}
		
		return super.equals(obj);
	}
}
