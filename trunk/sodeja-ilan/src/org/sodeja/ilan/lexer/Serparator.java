package org.sodeja.ilan.lexer;

public enum Serparator implements Token {
	LBRACE('('),
	RBRACE(')'),
	LCBRACE('{'),
	RCBRACE('}'),
	SEMI_COLON(';');
	
	private final char ch;
	
	private Serparator(char ch) {
		this.ch = ch;
	}

	public char getCh() {
		return ch;
	}


	@Override
	public String toString() {
		return "S[" + ch + "]";
	}
}
