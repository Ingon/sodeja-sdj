package org.sodeja.il;

public class ClassExpression implements Expression {
	public final String name;
	public final BlockExpression block;
	
	public ClassExpression(String name, BlockExpression block) {
		this.name = name;
		this.block = block;
	}
}
