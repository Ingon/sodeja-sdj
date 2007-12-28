package org.sodeja.ilan.lexer;

public class Identifier implements Token {
	public final String name;

	public Identifier(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "I[" + name + "]";
	}
}
