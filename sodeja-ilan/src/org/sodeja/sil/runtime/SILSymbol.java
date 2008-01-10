package org.sodeja.sil.runtime;

public class SILSymbol implements SILObject {
	private final String value;
	
	public SILSymbol(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof SILSymbol)) {
			return false;
		}
		
		return value.equals(((SILSymbol) obj).value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public String toString() {
		return "#" + value;
	}

	@Override
	public SILClass getType() {
		throw new UnsupportedOperationException();
	}
}
