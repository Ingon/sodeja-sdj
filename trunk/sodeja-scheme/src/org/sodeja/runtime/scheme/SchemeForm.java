package org.sodeja.runtime.scheme;

import org.sodeja.runtime.Evaluator;
import org.sodeja.runtime.Form;
import org.sodeja.runtime.Frame;
import org.sodeja.runtime.scheme.model.Combination;

public abstract class SchemeForm implements Form<SchemeExpression> {
	@Override
	public Object eval(Evaluator<SchemeExpression> runtime,
			Frame<SchemeExpression> frame, SchemeExpression expression) {
		return evalDelegate(runtime, frame, (Combination) expression);
	}
	
	protected abstract Object evalDelegate(Evaluator<SchemeExpression> evaluator,
			Frame<SchemeExpression> frame, Combination expression);
}
