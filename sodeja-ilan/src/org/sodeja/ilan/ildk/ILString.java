package org.sodeja.ilan.ildk;

public class ILString extends ILObject {

	public final String value;
	
	public ILString(String value) {
		this.value = value;
	}

	@Override
	protected ILSymbol getILClassName() {
		return new ILSymbol("ILString");
	}
}
