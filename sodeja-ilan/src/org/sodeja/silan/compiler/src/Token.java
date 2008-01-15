package org.sodeja.silan.compiler.src;

public class Token {
	public final String value;
	public final TokenType type;
	
	public Token(String value, TokenType type) {
		this.value = value;
		this.type = type;
	}

	@Override
	public String toString() {
		return "(" + type + ":" + value + ")";
	}
}
