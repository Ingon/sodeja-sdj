package org.sodeja.il.model;

import java.util.List;

import org.sodeja.collections.ListUtils;

public class FunctionExpression extends LambdaExpression {
	public final VariableExpression name;
	
	public FunctionExpression(List<VariableExpression> params, 
			Expression expression) {
		super(ListUtils.tail(params), expression);
		this.name = ListUtils.head(params);
	}
}
