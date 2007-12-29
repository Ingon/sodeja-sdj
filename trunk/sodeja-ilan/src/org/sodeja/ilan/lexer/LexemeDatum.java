package org.sodeja.ilan.lexer;

public abstract class LexemeDatum<T> implements Datum {
	public final T value;

	public LexemeDatum(T value) {
		this.value = value;
	}
}
