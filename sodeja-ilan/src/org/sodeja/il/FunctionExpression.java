package org.sodeja.il;

import java.util.List;

public class FunctionExpression implements Expression {
	public final VariableExpression name;
	public final List<VariableExpression> params;
	public final Expression expression;
	
	public FunctionExpression(VariableExpression name, List<VariableExpression> params, 
			Expression expression) {
		this.name = name;
		this.params = params;
		this.expression = expression;
	}
}
