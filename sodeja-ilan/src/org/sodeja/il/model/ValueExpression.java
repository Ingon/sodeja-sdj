package org.sodeja.il.model;

import org.sodeja.il.runtime.Context;
import org.sodeja.il.runtime.ILClass;
import org.sodeja.il.runtime.ILObject;

public class ValueExpression<T> implements Expression {

	public final String valueClass;
	public final T value;
	
	public ValueExpression(String valueClass, T value) {
		this.valueClass = valueClass;
		this.value = value;
	}

	@Override
	public ILObject eval(Context ctx) {
		ILClass clazz = ctx.getRoot().getClassByName(valueClass);
		return clazz.makeInstance(value);
	}
}
