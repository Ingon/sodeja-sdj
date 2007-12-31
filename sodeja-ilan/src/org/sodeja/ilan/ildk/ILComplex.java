package org.sodeja.ilan.ildk;

public class ILComplex extends ILNumber {
	@Override
	protected ILSymbol getILClassName() {
		return new ILSymbol("ILComplex");
	}
}
