package org.sodeja.il.model;

import org.sodeja.il.runtime.Context;
import org.sodeja.il.runtime.ILObject;
import org.sodeja.il.runtime.ILSymbol;

public class SetExpression implements Expression {
	public final VariableExpression name;
	public final Expression value;
	
	public SetExpression(VariableExpression name, Expression value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public ILObject eval(Context ctx) {
		ILSymbol symbol = (ILSymbol) name.eval(ctx);
		ctx.define(symbol, value.eval(ctx));
		return symbol;
	}
}
