package org.sodeja.runtime.scheme2;

import org.sodeja.runtime.Evaluator;
import org.sodeja.runtime.Frame;

public class SetExpression implements CompiledSchemeExpression {

	private final NameExpression name;
	private final CompiledSchemeExpression expression;
	
	public SetExpression(NameExpression name, CompiledSchemeExpression expression) {
		this.name = name;
		this.expression = expression;
	}

	@Override
	public Object eval(Evaluator<CompiledSchemeExpression> evaluator,
			Frame<CompiledSchemeExpression> frame) {

		Object value = evaluator.eval(frame, expression);
		frame.setObject(name, value);
		return value;
	}
}
