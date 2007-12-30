package org.sodeja.ilan.parser;

import org.sodeja.ilan.ildk.ILObject;
import org.sodeja.ilan.runtime.IContext;

public class ValueExpression implements Expression {
	public final ILObject value;

	public ValueExpression(ILObject value) {
		this.value = value;
	}

	@Override
	public ILObject eval(IContext context) {
		return value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
