package org.sodeja.ilan.parser;

import java.util.List;

import org.sodeja.ilan.runtime.ILFrame;

public class Application implements Expression {
	public final List<SimpleExpression> expressions;

	public Application(List<SimpleExpression> expressions) {
		this.expressions = expressions;
	}

	@Override
	public Object eval(ILFrame frame) {
		throw new UnsupportedOperationException();
	}
}
