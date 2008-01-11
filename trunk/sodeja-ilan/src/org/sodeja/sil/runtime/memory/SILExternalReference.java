package org.sodeja.sil.runtime.memory;

public class SILExternalReference extends SILReference {
	protected SILExternalReference(Integer id) {
		super(id);
	}

	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof SILExternalReference)) {
			return false;
		}
		
		return super.equals(obj);
	}
}
