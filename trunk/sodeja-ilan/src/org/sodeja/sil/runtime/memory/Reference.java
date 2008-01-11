package org.sodeja.sil.runtime.memory;

abstract class Reference {
	protected final ObjectManager manager;
	protected final Integer id;
	
	protected Reference(ObjectManager manager, Integer id) {
		this.manager = manager;
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof Reference)) {
			return false;
		}
		return id.equals(((Reference) obj).id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}
}
