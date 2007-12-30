package org.sodeja.ilan.runtime;

import org.sodeja.ilan.ildk.ILObject;
import org.sodeja.ilan.parser.Expression;

public class Runtime {
	private Process rootProcess;
	
	public Runtime() {
		rootProcess = new Process();
	}
	
	public ILObject eval(Expression expression) {
		return rootProcess.eval(expression);
	}
}
