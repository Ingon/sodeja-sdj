package org.sodeja.ilan.ildk;

public class ILSymbol extends ILObject {
	public final String value;

	public ILSymbol(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof ILSymbol)) {
			return false;
		}
		
		return value.equals(((ILSymbol) obj).value);
	}

	@Override
	public String toString() {
		return value;
	}
}
