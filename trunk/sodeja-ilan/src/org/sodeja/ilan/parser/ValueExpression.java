package org.sodeja.ilan.parser;

import org.sodeja.ilan.runtime.ILFrame;

public class ValueExpression<T> implements SimpleExpression {
	public final T value;

	public ValueExpression(T value) {
		this.value = value;
	}

	@Override
	public Object eval(ILFrame frame) {
		return value;
	}
}
