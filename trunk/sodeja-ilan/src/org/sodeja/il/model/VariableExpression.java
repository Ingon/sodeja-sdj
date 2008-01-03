package org.sodeja.il.model;

import org.sodeja.il.runtime.Context;

public class VariableExpression implements Expression {
	public final String name;

	// TODO ugly hack, should be done on parse time :)
	private Integer intVal;
	
	public VariableExpression(String name) {
		this.name = name;
		
		try {
			intVal = Integer.parseInt(name);
		} catch(NumberFormatException exc) {
			intVal = null;
		}
	}

	@Override
	public Object eval(Context ctx) {
		if(intVal != null) {
			return intVal;
		}
		return ctx.get(name);
	}
	
	@Override
	public String toString() {
		return "Var[" + name + "]";
	}
}
