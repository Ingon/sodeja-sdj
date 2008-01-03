package org.sodeja.il.model;

import java.util.List;

import org.sodeja.il.runtime.Context;
import org.sodeja.il.runtime.ILObject;

public class LambdaExpression implements Expression {
	public final List<VariableExpression> params;
	public final Expression expression;
	
	public LambdaExpression(List<VariableExpression> params, Expression expression) {
		this.params = params;
		this.expression = expression;
	}

	@Override
	public ILObject eval(Context ctx) {
		throw new UnsupportedOperationException();
	}
}
