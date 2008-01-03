package org.sodeja.il.model;

import java.util.List;

import org.sodeja.il.runtime.Context;

public class BlockExpression implements Expression {
	public final List<Expression> expressions;

	public BlockExpression(List<Expression> expressions) {
		this.expressions = expressions;
	}

	@Override
	public Object eval(Context ctx) {
		throw new UnsupportedOperationException();
	}
}
