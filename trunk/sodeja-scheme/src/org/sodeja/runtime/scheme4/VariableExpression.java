package org.sodeja.runtime.scheme4;

public abstract class VariableExpression implements CompiledSchemeExpression {
	
	public final NameExpression name;
	protected Object temp;
	
	public VariableExpression(NameExpression name) {
		this.name = name;
	}
}
