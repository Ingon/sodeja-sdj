package org.sodeja.il.model;

import org.sodeja.il.runtime.Context;
import org.sodeja.il.runtime.ILClass;
import org.sodeja.il.runtime.ILObject;

public class VariableExpression implements Expression {
	public final String name;

	public VariableExpression(String name) {
		this.name = name;
	}

	@Override
	public ILObject eval(Context ctx) {
		ILClass clazz = ctx.getRoot().getClassByName("ILSymbol");
		return clazz.makeInstance(name);
	}
	
	@Override
	public String toString() {
		return "Var[" + name + "]";
	}
}
