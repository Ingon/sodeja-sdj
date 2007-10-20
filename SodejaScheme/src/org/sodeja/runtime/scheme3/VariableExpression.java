package org.sodeja.runtime.scheme3;

public abstract class VariableExpression implements CompiledSchemeExpression {
	
	public final NameExpression name;
	
	public VariableExpression(NameExpression name) {
		this.name = name;
	}
}
