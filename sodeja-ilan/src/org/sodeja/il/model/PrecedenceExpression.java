package org.sodeja.il.model;

import org.sodeja.il.runtime.Context;

public class PrecedenceExpression implements Expression {
	public final Expression expression;

	public PrecedenceExpression(Expression expression) {
		this.expression = expression;
	}

	@Override
	public Object eval(Context ctx) {
		throw new UnsupportedOperationException();
	}
}
