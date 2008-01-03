package org.sodeja.il.model;

public class ClassExpression implements Expression {
	public final VariableExpression name;
	public final BlockExpression block;
	
	public ClassExpression(VariableExpression name, BlockExpression block) {
		this.name = name;
		this.block = block;
	}
}
