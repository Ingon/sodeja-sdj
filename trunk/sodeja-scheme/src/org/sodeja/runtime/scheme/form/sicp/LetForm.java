package org.sodeja.runtime.scheme.form.sicp;

import org.sodeja.runtime.Evaluator;
import org.sodeja.runtime.Frame;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.SchemeForm;
import org.sodeja.runtime.scheme.SchemeFrame;
import org.sodeja.runtime.scheme.Utils;
import org.sodeja.runtime.scheme.model.Combination;
import org.sodeja.runtime.scheme.model.Symbol;

public class LetForm extends SchemeForm {
	@Override
	protected Object evalDelegate(Evaluator<SchemeExpression> evaluator,
			Frame<SchemeExpression> frame, Combination expression) {
		if (expression.size() < 3) {
			throw new IllegalArgumentException(
					"Expect at least two expressions - var binding and and executed");
		}
		
		if(! (expression.get(1) instanceof Combination)) {
			throw new IllegalArgumentException("Var bingings part has form ((<var1> <exp1>) (<var1> <exp1>)...)");
		}
		
		SchemeFrame newFrame = new SchemeFrame(frame);
		Combination bindings = (Combination) expression.get(1);
		for(SchemeExpression bindingExpression : bindings.parts) {
			bind(evaluator, frame, newFrame, bindingExpression);
		}
		
		return Utils.evalSingle(evaluator, newFrame, expression.parts, 2);
	}

	private void bind(Evaluator<SchemeExpression> evaluator, Frame<SchemeExpression> frame, 
			Frame<SchemeExpression> newFrame, SchemeExpression bindingExpression) {
		
		if(! (bindingExpression instanceof Combination)) {
			throw new IllegalArgumentException("Var binging has form (<var> <exp>)");
		}
		
		Combination bindingComb = (Combination) bindingExpression;
		if (bindingComb.size() != 2) {
			throw new IllegalArgumentException("Var binging has form (<var> <exp>)");
		}
		
		SchemeExpression symbolExpression = bindingComb.get(0);
		if(! (symbolExpression instanceof Symbol)) {
			throw new IllegalArgumentException("Var binging has form (<var> <exp>)");
		}
		
		// If we construct frame now and use it in every consequent expression maybe letrec?
		newFrame.addObject((Symbol) symbolExpression, evaluator.eval(frame, bindingComb.get(1)));
	}
}
