package org.sodeja.ilan.parser;

import org.sodeja.ilan.ildk.ILObject;
import org.sodeja.ilan.runtime.Context;

public class ValueExpression implements Expression {
	public final ILObject value;

	public ValueExpression(ILObject value) {
		this.value = value;
	}

	@Override
	public ILObject eval(Context context) {
		return value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
