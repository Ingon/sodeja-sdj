package org.sodeja.silan.compiler.src;

public class NestedExpression implements Primary {
	public final Statement statement;

	public NestedExpression(Statement statement) {
		this.statement = statement;
	}
}
