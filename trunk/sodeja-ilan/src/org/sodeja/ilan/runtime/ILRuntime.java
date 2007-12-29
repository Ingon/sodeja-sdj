package org.sodeja.ilan.runtime;

import org.sodeja.ilan.parser.Expression;
import org.sodeja.ilan.parser.Program;

public class ILRuntime {
	private ILFrame rootFrame;

	public Object execute(Program program) {
		Object result = null;
		for(Expression exp : program.expressions) {
			result = exp.eval(rootFrame);
		}
		return result;
	}
}
