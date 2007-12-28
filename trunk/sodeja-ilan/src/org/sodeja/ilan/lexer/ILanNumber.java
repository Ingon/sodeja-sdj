package org.sodeja.ilan.lexer;

public class ILanNumber implements ILanToken {
	public final Integer value;
	
	public ILanNumber(Integer value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "N[" + value + "]";
	}
}
