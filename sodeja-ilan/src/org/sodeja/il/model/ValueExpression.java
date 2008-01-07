package org.sodeja.il.model;

import org.sodeja.il.runtime.Context;
import org.sodeja.il.runtime.SDK;
import org.sodeja.il.sdk.ILObject;

public class ValueExpression implements Expression {

	public final ILObject value;
	
	public ValueExpression(Object value) {
		this.value = SDK.getInstance().makeInstance(value);
	}

	@Override
	public ILObject eval(Context ctx) {
		return value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
