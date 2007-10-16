package org.sodeja.scheme.parse.model;

public abstract class SimpleExpression<T> implements Expression {
	public final String value;
	
	public SimpleExpression(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
