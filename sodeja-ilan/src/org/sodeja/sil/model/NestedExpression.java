package org.sodeja.sil.model;

public class NestedExpression implements Operand {
	public final Statement statement;

	public NestedExpression(Statement statement) {
		this.statement = statement;
	}
}
