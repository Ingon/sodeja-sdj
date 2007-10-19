package org.sodeja.runtime.scheme3;

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
		
		Object[] values = new Object[bindings.size()];
		for(int i = 0, n = bindings.size();i < n;i++) {
			Pair<NameExpression, CompiledSchemeExpression> binding = bindings.get(i);
			values[i] = evaluator.eval(frame, binding.second);
		}
		
		CompiledSchemeFrame newFrame = realFrame.createChild(values);
		for(int i = 0, n = bindings.size();i < n;i++) {
			Pair<NameExpression, CompiledSchemeExpression> binding = bindings.get(i);
			newFrame.addObject(binding.first, values[i]);
		}
		
		return CompiledSchemeUtils.evalSingle(evaluator, newFrame, body);
	}
}
