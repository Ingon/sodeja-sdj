package org.sodeja.sil.runtime.memory;

public class ExternalReference extends Reference {
	protected ExternalReference(Integer id) {
		super(id);
	}

	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof ExternalReference)) {
			return false;
		}
		
		return super.equals(obj);
	}
}
