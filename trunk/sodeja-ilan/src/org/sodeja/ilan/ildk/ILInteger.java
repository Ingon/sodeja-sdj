package org.sodeja.ilan.ildk;

public class ILInteger extends ILNumber {
	public final Long value;

	public ILInteger(Long value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	protected ILSymbol getILClassName() {
		return new ILSymbol("ILInteger");
	}
}
