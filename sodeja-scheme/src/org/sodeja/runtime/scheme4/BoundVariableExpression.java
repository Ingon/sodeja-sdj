package org.sodeja.runtime.scheme4;

import org.sodeja.runtime.Evaluator;
import org.sodeja.runtime.Frame;

public class BoundVariableExpression extends VariableExpression {
	
	private final int scopeIndex;
	
	public BoundVariableExpression(NameExpression name, int scopeIndex) {
		super(name);
		this.scopeIndex = scopeIndex;
	}

	@Override
	public Object eval(Evaluator<CompiledSchemeExpression> evaluator,
			Frame<CompiledSchemeExpression> frame) {
		
		CompiledSchemeFrame realFrame = (CompiledSchemeFrame) frame;
		return realFrame.getActualParameters()[scopeIndex];
	}
}
