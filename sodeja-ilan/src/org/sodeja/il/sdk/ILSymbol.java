package org.sodeja.il.sdk;

import org.sodeja.il.runtime.SDK;

public class ILSymbol implements ILObject {
	private final String value;
	
	public ILSymbol(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof ILSymbol)) {
			return false;
		}
		
		return value.equals(((ILSymbol) obj).value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}
	
	@Override
	public String toString() {
		return value;
	}

	@Override
	public ILClass getType() {
		return SDK.getInstance().getSymbolType();
	}
}
