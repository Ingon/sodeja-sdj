package org.sodeja.runtime.scheme2;

import org.sodeja.runtime.scheme.model.Symbol;

public class NameExpression extends ValueExpression<Symbol> {
	public NameExpression(Symbol value) {
		super(value);
	}

	@Override
	public String toString() {
		return value.value;
	}
}
