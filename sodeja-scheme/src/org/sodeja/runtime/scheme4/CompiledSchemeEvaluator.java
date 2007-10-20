package org.sodeja.runtime.scheme4;

import org.sodeja.runtime.Frame;
import org.sodeja.runtime.Library;
import org.sodeja.runtime.impl.CompiledEvaluatorImpl;

public class CompiledSchemeEvaluator extends CompiledEvaluatorImpl<CompiledSchemeExpression> {
	public CompiledSchemeEvaluator(Library<CompiledSchemeExpression> library) {
		super(library, new CompiledSchemeFrame());
	}

	@Override
	public Object eval(Frame<CompiledSchemeExpression> frame, CompiledSchemeExpression expression) {
		return super.eval(frame, expression);
	}
}
