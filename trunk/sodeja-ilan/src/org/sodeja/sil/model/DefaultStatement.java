package org.sodeja.sil.model;

import java.util.List;

public class DefaultStatement implements Statement {
	public final List<Assignment> assignments;
	public final Expression expression;
	
	public DefaultStatement(List<Assignment> assignments, Expression expression) {
		this.assignments = assignments;
		this.expression = expression;
	}
}
