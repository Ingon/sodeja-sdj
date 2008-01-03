package org.sodeja.il;

import java.util.List;

public class LambdaExpression implements Expression {
	public final List<VariableExpression> params;
	public final Expression expression;
	
	public LambdaExpression(List<VariableExpression> params, Expression expression) {
		this.params = params;
		this.expression = expression;
	}
}
