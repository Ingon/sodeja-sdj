package org.sodeja.sil.runtime.memory;

abstract class Reference {
	public final Integer id;
	
	protected Reference(Integer id) {
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
