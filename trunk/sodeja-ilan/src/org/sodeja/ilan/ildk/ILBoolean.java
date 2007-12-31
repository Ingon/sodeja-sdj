package org.sodeja.ilan.ildk;

public class ILBoolean extends ILObject {
	
	public final Boolean value;
	
	public ILBoolean(Boolean value) {
		this.value = value;
	}

	public boolean isTrue() {
		return value;
	}

	@Override
	protected ILSymbol getILClassName() {
		return new ILSymbol("ILBoolean");
	}
}
