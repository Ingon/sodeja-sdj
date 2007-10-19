package org.sodeja.runtime.scheme2;

import org.sodeja.runtime.Evaluator;
import org.sodeja.runtime.Frame;
import org.sodeja.runtime.scheme.model.Symbol;

public class VariableExpression implements CompiledSchemeExpression {
	
	public final NameExpression name;
	
	public VariableExpression(Symbol name) {
		this.name = new NameExpression(name);
	}
	
	@Override
	public Object eval(Evaluator<CompiledSchemeExpression> evaluator, Frame<CompiledSchemeExpression> frame) {
		return frame.findObject(name);
	}
}
