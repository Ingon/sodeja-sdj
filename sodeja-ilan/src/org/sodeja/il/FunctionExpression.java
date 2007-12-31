package org.sodeja.il;

import java.util.List;

public class FunctionExpression implements Expression {
	public final List<String> params;
	public final Expression expression;
	
	public FunctionExpression(List<String> params, Expression expression) {
		this.params = params;
		this.expression = expression;
	}
}
