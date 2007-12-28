package org.sodeja.ilan.lexer;

public class Number implements Token {
	public final Integer value;
	
	public Number(Integer value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "N[" + value + "]";
	}
}
