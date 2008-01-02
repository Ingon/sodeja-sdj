package org.sodeja.il;

public class SetExpression implements Expression {
	public final VariableExpression name;
	public final Expression value;
	
	public SetExpression(VariableExpression name, Expression value) {
		this.name = name;
		this.value = value;
	}
}
