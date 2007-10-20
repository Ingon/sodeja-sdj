package org.sodeja.runtime.scheme4;

import org.sodeja.runtime.Evaluator;
import org.sodeja.runtime.Frame;

public class IfExpression implements CompiledSchemeExpression {

	private final CompiledSchemeExpression predicate;
	private final CompiledSchemeExpression consequent;
	private final CompiledSchemeExpression alternative;

	public IfExpression(CompiledSchemeExpression predicate,
			CompiledSchemeExpression consequent,
			CompiledSchemeExpression alternative) {
		this.predicate = predicate;
		this.consequent = consequent;
		this.alternative = alternative;
	}

	@Override
	public Object eval(Evaluator<CompiledSchemeExpression> evaluator,
			Frame<CompiledSchemeExpression> frame) {
		
		Object predicateValue = predicate.eval(evaluator, frame);
		if(! (predicateValue instanceof Boolean)) {
			throw new IllegalArgumentException(
				"Predicate does not returns boolean!");
		}
		
		if (((Boolean) predicateValue).booleanValue()) {
			return consequent.eval(evaluator, frame);
		} else {
			return alternative.eval(evaluator, frame);
		}
	}
}
