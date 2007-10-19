package org.sodeja.runtime.scheme2;

import java.util.List;

import org.sodeja.functional.Pair;
import org.sodeja.runtime.Evaluator;
import org.sodeja.runtime.Frame;

public class LetExpression implements CompiledSchemeExpression {

	private final List<Pair<NameExpression, CompiledSchemeExpression>> bindings;
	private final List<CompiledSchemeExpression> body;
	
	public LetExpression(List<Pair<NameExpression, CompiledSchemeExpression>> bindings,
			List<CompiledSchemeExpression> body) {
		this.bindings = bindings;
		this.body = body;
	}

	@Override
	public Object eval(Evaluator<CompiledSchemeExpression> evaluator,
			Frame<CompiledSchemeExpression> frame) {
		final CompiledSchemeFrame realFrame = (CompiledSchemeFrame) frame;
		CompiledSchemeFrame newFrame = realFrame.createChild();
		
		for(Pair<NameExpression, CompiledSchemeExpression> binding : bindings) {
			newFrame.addObject(binding.first, evaluator.eval(frame, binding.second));
		}
		
		return CompiledSchemeUtils.evalSingle(evaluator, newFrame, body);
	}
}
