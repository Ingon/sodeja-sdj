package org.sodeja.ilan.parser;

import java.util.List;

public class ILanProgram implements ILanExpression {
	public final List<ILanExpression> expressions;

	public ILanProgram(List<ILanExpression> expressions) {
		this.expressions = expressions;
	}
}
