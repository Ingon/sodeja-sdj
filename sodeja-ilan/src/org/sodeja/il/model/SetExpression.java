package org.sodeja.il.model;

import org.sodeja.il.runtime.Context;

public class SetExpression implements Expression {
	public final VariableExpression name;
	public final Expression value;
	
	public SetExpression(VariableExpression name, Expression value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public Object eval(Context ctx) {
		ctx.define(name.name, value.eval(ctx));
		return name;
	}
}
