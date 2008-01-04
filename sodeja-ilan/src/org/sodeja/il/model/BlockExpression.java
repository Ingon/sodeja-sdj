package org.sodeja.il.model;

import java.util.List;

import org.sodeja.il.runtime.Context;
import org.sodeja.il.runtime.ILObject;

public class BlockExpression implements Expression {
	public final List<Expression> expressions;

	public BlockExpression(List<Expression> expressions) {
		this.expressions = expressions;
	}

	@Override
	public ILObject eval(Context ctx) {
		// TODO maybe return a list?
		ILObject result = null;
		for(Expression expr : expressions) {
			result = expr.eval(ctx);
		}
		return result;
	}
}
