package org.sodeja.ilan.runtime;

import org.sodeja.ilan.ildk.ILObject;
import org.sodeja.ilan.parser.Expression;

public class Process {
	private Context context;

	public Process() {
		context = new ChildContext();
	}
	
	public ILObject eval(Expression expression) {
		return expression.eval(context);
	}
}
