package org.sodeja.ilan.lexer;

public class IdentifierDatum extends LexemeDatum<String> {
	public IdentifierDatum(String value) {
		super(value);
	}

	@Override
	public String toString() {
		return "I(" + value + ")";
	}
}
