package org.sodeja.il.model;

import org.sodeja.il.runtime.Context;
import org.sodeja.il.runtime.ILObject;
import org.sodeja.il.runtime.SDK;

public class ValueExpression implements Expression {

	public final ILObject value;
	
	public ValueExpression(String valueClass, Object value) {
		this.value = SDK.getInstance().makeInstance(valueClass, value);
	}

	@Override
	public ILObject eval(Context ctx) {
		return value;
	}
}
