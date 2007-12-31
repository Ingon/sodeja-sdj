package org.sodeja.ilan.ildk;

public class ILCharacter extends ILObject {

	public final Character value;
	
	public ILCharacter(Character value) {
		this.value = value;
	}

	@Override
	protected ILSymbol getILClassName() {
		return new ILSymbol("ILCharacter");
	}
}
