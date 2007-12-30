package org.sodeja.ilan.runtime;

import org.sodeja.ilan.ildk.ILObject;
import org.sodeja.ilan.parser.Expression;

public class Process {
	private IContext context;

	public Process() {
		context = new IContext();
	}
	
	public ILObject eval(Expression expression) {
		return expression.eval(context);
	}
}
