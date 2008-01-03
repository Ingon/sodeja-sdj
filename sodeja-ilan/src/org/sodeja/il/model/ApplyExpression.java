package org.sodeja.il.model;

import java.util.List;

public class ApplyExpression implements Expression {
	public final List<Expression> expressions;

	public ApplyExpression(List<Expression> expressions) {
		this.expressions = expressions;
	}
}
