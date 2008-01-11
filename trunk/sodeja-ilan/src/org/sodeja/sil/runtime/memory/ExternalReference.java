package org.sodeja.sil.runtime.memory;

public class ExternalReference extends Reference {
	protected ExternalReference(ObjectManager manager, Integer id) {
		super(manager, id);
	}

	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof ExternalReference)) {
			return false;
		}
		
		return super.equals(obj);
	}
	
	public Object getValue() {
		return manager.getLink(this);
	}
}
