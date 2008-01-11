package org.sodeja.sil.runtime;

public class SILExternalReference extends SILReference {
	public SILExternalReference(Integer id) {
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
