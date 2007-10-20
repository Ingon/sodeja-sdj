package org.sodeja.runtime.scheme3;

import java.util.List;

import org.sodeja.runtime.Evaluator;
import org.sodeja.runtime.Frame;

public class BeginExpression implements CompiledSchemeExpression {
	
	private final List<CompiledSchemeExpression> body;
	
	public BeginExpression(List<CompiledSchemeExpression> body) {
		this.body = body;
	}

	@Override
	public Object eval(Evaluator<CompiledSchemeExpression> evaluator,
			Frame<CompiledSchemeExpression> frame) {
		
		return CompiledSchemeUtils.evalSingle(evaluator, frame, body);
	}
}
