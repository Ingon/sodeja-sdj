package org.sodeja.runtime.scheme3;

import java.util.List;

import org.sodeja.functional.Pair;
import org.sodeja.runtime.Evaluator;
import org.sodeja.runtime.Frame;

public class CondExpression implements CompiledSchemeExpression {
	
	private final List<Pair<CompiledSchemeExpression, List<CompiledSchemeExpression>>> clauses;
	
	public CondExpression(List<Pair<CompiledSchemeExpression, List<CompiledSchemeExpression>>> clauses) {
		this.clauses = clauses;
	}

	@Override
	public Object eval(Evaluator<CompiledSchemeExpression> evaluator,
			Frame<CompiledSchemeExpression> frame) {
		
		for(Pair<CompiledSchemeExpression, List<CompiledSchemeExpression>> clause : clauses) {
			if(evalToken(evaluator, frame, clause.first)) {
				return CompiledSchemeUtils.evalSingle(evaluator, frame, clause.second);
			}
		}
		
		throw new RuntimeException("No clause matches!");
	}

	private Boolean evalToken(Evaluator<CompiledSchemeExpression> evaluator, 
			Frame<CompiledSchemeExpression> frame, CompiledSchemeExpression predicate) {
		Object obj = evaluator.eval(frame, predicate);
		if(! (obj instanceof Boolean)) {
			throw new IllegalArgumentException("Predicate does not returns boolean!");
		}

		return (Boolean) obj;
	}
}
