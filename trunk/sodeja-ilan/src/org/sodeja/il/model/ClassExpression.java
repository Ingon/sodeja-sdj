package org.sodeja.il.model;

import org.sodeja.il.runtime.Context;
import org.sodeja.il.runtime.ILObject;

public class ClassExpression implements Expression {
	public final VariableExpression name;
	public final BlockExpression block;
	
	public ClassExpression(VariableExpression name, BlockExpression block) {
		this.name = name;
		this.block = block;
	}

	@Override
	public ILObject eval(Context ctx) {
		throw new UnsupportedOperationException();
	}
}
