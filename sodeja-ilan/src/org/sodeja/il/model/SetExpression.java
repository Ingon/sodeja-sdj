package org.sodeja.il.model;

import org.sodeja.il.runtime.Context;
import org.sodeja.il.sdk.ILObject;

public class SetExpression implements Expression {
	public final VariableExpression name;
	public final Expression value;
	
	public SetExpression(VariableExpression name, Expression value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public ILObject eval(Context ctx) {
		ctx.define(name.name, value.eval(ctx));
		return name.name;
	}

	@Override
	public String toString() {
		return name + "=" + value;
	}
}
