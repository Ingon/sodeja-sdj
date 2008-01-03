package org.sodeja.il.model;

import org.sodeja.il.runtime.Context;

public class ClassExpression implements Expression {
	public final VariableExpression name;
	public final BlockExpression block;
	
	public ClassExpression(VariableExpression name, BlockExpression block) {
		this.name = name;
		this.block = block;
	}

	@Override
	public Object eval(Context ctx) {
		throw new UnsupportedOperationException();
	}
}
