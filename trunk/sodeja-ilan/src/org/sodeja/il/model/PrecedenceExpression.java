package org.sodeja.il.model;

import org.sodeja.il.runtime.Context;
import org.sodeja.il.sdk.ILObject;

public class PrecedenceExpression implements Expression {
	public final Expression expression;

	public PrecedenceExpression(Expression expression) {
		this.expression = expression;
	}

	@Override
	public ILObject eval(Context ctx) {
		return expression.eval(ctx);
	}

	@Override
	public String toString() {
		return "(" + expression.toString() + ")";
	}
}
