package org.sodeja.il;

import java.util.List;

public class PrecedenceExpression implements Expression {
	public final List<Expression> expressions;

	public PrecedenceExpression(List<Expression> expressions) {
		this.expressions = expressions;
	}
}
