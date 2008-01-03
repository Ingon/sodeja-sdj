package org.sodeja.il.model;

import org.sodeja.il.runtime.Context;
import org.sodeja.il.runtime.ILObject;

public class PrecedenceExpression implements Expression {
	public final Expression expression;

	public PrecedenceExpression(Expression expression) {
		this.expression = expression;
	}

	@Override
	public ILObject eval(Context ctx) {
		throw new UnsupportedOperationException();
	}
}
