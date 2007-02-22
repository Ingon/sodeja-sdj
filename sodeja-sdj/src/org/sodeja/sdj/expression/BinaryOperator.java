package org.sodeja.sdj.expression;

public enum BinaryOperator {
	PLUS("+"),
	MINUS("-"),
	STAR("*"),
	DIVISION("/"),
	LESS("<"),
	LESS_EQUAL("<="),
	EQUAL("=="),
	NOT_EQUAL("~="),
	MORE_EQUAL(">="),
	MORE(">"),
	AND("&"),
	OR("|");
	
	public final String text;
	
	private BinaryOperator(String text) {
		this.text = text;
	}
}
