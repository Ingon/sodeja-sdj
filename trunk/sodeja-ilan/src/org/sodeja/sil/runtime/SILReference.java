package org.sodeja.sil.runtime;

public abstract class SILReference {
	public final Integer id;
	
	public SILReference(Integer id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof SILReference)) {
			return false;
		}
		return id.equals(((SILReference) obj).id);
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
