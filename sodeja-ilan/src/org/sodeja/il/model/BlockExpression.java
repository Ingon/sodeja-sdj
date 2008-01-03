package org.sodeja.il.model;

import java.util.List;

public class BlockExpression implements Expression {
	public final List<Expression> expressions;

	public BlockExpression(List<Expression> expressions) {
		this.expressions = expressions;
	}
}
