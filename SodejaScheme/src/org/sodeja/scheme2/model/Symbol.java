package org.sodeja.scheme2.model;

public class Symbol implements Token {
	public final String value;
	
	public Symbol(String value) {
		this.value = value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof Symbol)) {
			return false;
		}
		
		return this.value.equals(((Symbol) obj).value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public String toString() {
		return value;
	}
}
