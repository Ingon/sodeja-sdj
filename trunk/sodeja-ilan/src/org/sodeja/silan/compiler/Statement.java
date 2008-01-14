package org.sodeja.silan.compiler;

public class Statement {
	public final String assignment;
	public final Expression expression;
	
	public Statement(String assignment, Expression expression) {
		this.assignment = assignment;
		this.expression = expression;
	}
}
