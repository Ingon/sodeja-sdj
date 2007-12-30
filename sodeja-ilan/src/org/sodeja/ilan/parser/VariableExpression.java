package org.sodeja.ilan.parser;

import org.sodeja.ilan.ildk.ILObject;
import org.sodeja.ilan.ildk.ILSymbol;
import org.sodeja.ilan.runtime.IContext;

public class VariableExpression implements Expression {
	public final ILSymbol symbol;

	public VariableExpression(ILSymbol symbol) {
		this.symbol = symbol;
	}

	@Override
	public ILObject eval(IContext context) {
		return context.get(symbol);
	}

	@Override
	public String toString() {
		return symbol.toString();
	}
}
