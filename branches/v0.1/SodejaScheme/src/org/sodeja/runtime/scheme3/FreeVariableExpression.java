package org.sodeja.runtime.scheme3;

import org.sodeja.runtime.Evaluator;
import org.sodeja.runtime.Frame;

public class FreeVariableExpression extends VariableExpression {
	public FreeVariableExpression(NameExpression name) {
		super(name);
	}

	@Override
	public Object eval(Evaluator<CompiledSchemeExpression> evaluator, Frame<CompiledSchemeExpression> frame) {
		return frame.findObject(name);
	}
}
