package org.sodeja.ilan.lexer;

public class IdentifierToken implements Token {
	public final String name;

	public IdentifierToken(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "I[" + name + "]";
	}
}
