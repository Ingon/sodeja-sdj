package org.sodeja.runtime.scheme.form.sicp;

import org.sodeja.runtime.Evaluator;
import org.sodeja.runtime.Frame;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.SchemeForm;
import org.sodeja.runtime.scheme.SchemeFrame;
import org.sodeja.runtime.scheme.Utils;
import org.sodeja.runtime.scheme.model.Combination;
import org.sodeja.runtime.scheme.model.Symbol;

public class CondForm extends SchemeForm {
	@Override
	public Object evalDelegate(Evaluator<SchemeExpression> evaluator, 
			SchemeFrame frame, Combination expression) {
		
		for(int i = 1, n = expression.size();i < n;i++) {
			SchemeExpression clauseExp = expression.get(i);
			if(! (clauseExp instanceof Combination)) {
				throw new IllegalArgumentException("Every clause has (<predicate> <action>) form!");
			}
			
			Combination clause = (Combination) clauseExp;
			if(clause.size() != 2) {
				throw new IllegalArgumentException("Every clause has (<predicate> <action>) form!");
			}
			
			SchemeExpression predicate = clause.get(0);
			Boolean evalResult = evalToken(evaluator, frame, predicate);
			if(! evalResult.booleanValue()) {
				continue;
			}

			return Utils.evalSingle(evaluator, frame, clause.parts, 1);
		}
		
		throw new RuntimeException("No clause matches!");
	}

	private Boolean evalToken(Evaluator<SchemeExpression> runtime, 
			Frame<SchemeExpression> frame, SchemeExpression predicate) {
		if(predicate instanceof Symbol && ((Symbol) predicate).value.equals("else")) {
			return Boolean.TRUE;
		}
		
		Object obj = runtime.eval(frame, predicate);
		if(! (obj instanceof Boolean)) {
			throw new IllegalArgumentException("Predicate does not returns boolean!");
		}

		return (Boolean) obj;
	}
}
