package org.sodeja.scheme.parse.model;

public abstract class SimpleExpression<T> implements Expression {
	public final T value;
	
	public SimpleExpression(T value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
