package org.sodeja.il;

import java.util.List;

public class ApplyExpression implements Expression {
	public final List<String> symbols;

	public ApplyExpression(List<String> symbols) {
		this.symbols = symbols;
	}
}
