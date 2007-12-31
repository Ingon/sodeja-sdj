package org.sodeja.ilan.ildk;

public abstract class ILObject {
	protected abstract ILSymbol getILClassName();
	
	public final ILClass getILClass() {
		return ILClassFactory.getInstance().getMakeILClass(getILClassName());
	}
}
