package org.sodeja.runtime.scheme.model;

import org.sodeja.runtime.scheme.SchemeExpression;

public class Symbol implements SchemeExpression {
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
