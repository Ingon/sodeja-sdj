package org.sodeja.ilan.ildk;

public class ILReal extends ILNumber {

	public final Double value;
	
	public ILReal(Double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	protected ILSymbol getILClassName() {
		return new ILSymbol("ILReal");
	}
}
