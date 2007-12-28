package org.sodeja.ilan.parser;

public class ValueExpression<T> implements SimpleExpression {
	public final T value;

	public ValueExpression(T value) {
		this.value = value;
	}
}
