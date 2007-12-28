package org.sodeja.ilan.parser;

public class VariableExpression implements SimpleExpression {
	public final String name;

	public VariableExpression(String name) {
		this.name = name;
	}
}
