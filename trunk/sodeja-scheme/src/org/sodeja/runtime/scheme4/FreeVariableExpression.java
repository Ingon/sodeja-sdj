package org.sodeja.runtime.scheme4;

import org.sodeja.runtime.Evaluator;
import org.sodeja.runtime.Frame;

public class FreeVariableExpression extends VariableExpression {
	public FreeVariableExpression(NameExpression name) {
		super(name);
	}

	@Override
	public Object eval(Evaluator<CompiledSchemeExpression> evaluator, Frame<CompiledSchemeExpression> frame) {
		// TODO some notification mechanism		
		if(temp == null) {
			temp = frame.findObject(name);
		}
		return temp;
//		return frame.findObject(name);
	}
}
