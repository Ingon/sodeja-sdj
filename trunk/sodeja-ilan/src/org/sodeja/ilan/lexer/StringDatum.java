package org.sodeja.ilan.lexer;

public class StringDatum extends LexemeDatum<String> {
	public StringDatum(String value) {
		super(value);
	}

	@Override
	public String toString() {
		return "S(" + value + ")";
	}
}
