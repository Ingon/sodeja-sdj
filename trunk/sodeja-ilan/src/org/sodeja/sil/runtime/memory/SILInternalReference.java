package org.sodeja.sil.runtime.memory;

public class SILInternalReference extends SILReference {
	public SILInternalReference(Integer id) {
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
