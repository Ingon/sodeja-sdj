package org.sodeja.sil.runtime;

public class Reference {
	public final Integer id;

	public Reference(Integer id) {
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
}
