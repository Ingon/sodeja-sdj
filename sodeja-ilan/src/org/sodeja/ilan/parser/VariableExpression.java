package org.sodeja.ilan.parser;

import org.sodeja.ilan.runtime.ILFrame;

public class VariableExpression implements SimpleExpression {
	public final String name;

	public VariableExpression(String name) {
		this.name = name;
	}

	@Override
	public Object eval(ILFrame frame) {
		return frame.get(name);
	}
}
