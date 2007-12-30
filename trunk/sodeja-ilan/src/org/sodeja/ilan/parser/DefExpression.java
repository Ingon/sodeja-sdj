package org.sodeja.ilan.parser;

import org.sodeja.ilan.ildk.ILObject;
import org.sodeja.ilan.ildk.ILSymbol;
import org.sodeja.ilan.runtime.IContext;

public class DefExpression implements Expression {
	public final ILSymbol name;
	public final Expression value;
	
	public DefExpression(ILSymbol name, Expression value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public ILObject eval(IContext context) {
		context.def(name, value.eval(context));
		return name;
	}

	@Override
	public String toString() {
		return "(def " + name + " " + value + ")";
	}
}
