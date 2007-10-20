package org.sodeja.runtime.scheme.form.sicp;

import org.sodeja.runtime.Evaluator;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.SchemeForm;
import org.sodeja.runtime.scheme.SchemeFrame;
import org.sodeja.runtime.scheme.model.Combination;

public class IfForm extends SchemeForm {
	@Override
	protected Object evalDelegate(Evaluator<SchemeExpression> evaluator,
			SchemeFrame frame, Combination expression) {
		
		if (expression.size() != 4) {
			throw new IllegalArgumentException(
					"Expecting predicate, true action and false action");
		}
		
		Object obj = evaluator.eval(frame, expression.get(1));
		if (!(obj instanceof Boolean)) {
			throw new IllegalArgumentException(
					"Predicate does not returns boolean!");
		}
		
		if (((Boolean) obj).booleanValue()) {
			return evaluator.eval(frame, expression.get(2));
		} else {
			return evaluator.eval(frame, expression.get(3));
		}
	}
}
