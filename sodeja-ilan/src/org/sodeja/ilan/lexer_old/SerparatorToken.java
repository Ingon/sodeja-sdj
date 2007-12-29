package org.sodeja.ilan.lexer_old;

public enum SerparatorToken implements Token {
	LBRACE('('),
	RBRACE(')'),
	LCBRACE('{'),
	RCBRACE('}'),
	SEMI_COLON(';');
	
	private final char ch;
	
	private SerparatorToken(char ch) {
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
