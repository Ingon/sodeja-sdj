package org.sodeja.ilan.lexer;

public class CharacterDatum extends LexemeDatum<Character> {
	public CharacterDatum(Character value) {
		super(value);
	}

	@Override
	public String toString() {
		return "C(" + value + ")";
	}
}
