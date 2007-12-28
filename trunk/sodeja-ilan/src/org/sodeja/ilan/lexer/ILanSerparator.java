package org.sodeja.ilan.lexer;

public enum ILanSerparator implements ILanToken {
	LBRACE('('),
	RBRACE(')'),
	LCBRACE('{'),
	RCBRACE('}'),
	SEMI_COLON(';');
	
	private final char ch;
	
	private ILanSerparator(char ch) {
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
