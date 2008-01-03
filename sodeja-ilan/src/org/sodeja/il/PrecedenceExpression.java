package org.sodeja.il;

public class PrecedenceExpression implements Expression {
	public final Expression expression;

	public PrecedenceExpression(Expression expression) {
		this.expression = expression;
	}
}
