package org.sodeja.ilan.lexer_old;

public class NumberToken implements Token {
	public final Integer value;
	
	public NumberToken(Integer value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "N[" + value + "]";
	}
}
