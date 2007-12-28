package org.sodeja.ilan.parser;

import java.util.List;

public class Application implements Expression {
	public final List<SimpleExpression> expressions;

	public Application(List<SimpleExpression> expressions) {
		this.expressions = expressions;
	}
}
