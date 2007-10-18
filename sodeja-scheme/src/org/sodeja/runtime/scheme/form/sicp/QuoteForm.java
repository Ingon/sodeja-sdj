package org.sodeja.runtime.scheme.form.sicp;

import org.sodeja.runtime.Evaluator;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.SchemeForm;
import org.sodeja.runtime.scheme.SchemeFrame;
import org.sodeja.runtime.scheme.model.Combination;

public class QuoteForm extends SchemeForm {
	@Override
	protected Object evalDelegate(Evaluator<SchemeExpression> evaluator,
			SchemeFrame frame, Combination expression) {
		return expression.parts.subList(1, expression.size());
	}
}
