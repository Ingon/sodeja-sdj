package org.sodeja.runtime.scheme.form.sicp;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.runtime.Evaluator;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.SchemeForm;
import org.sodeja.runtime.scheme.SchemeFrame;
import org.sodeja.runtime.scheme.Utils;
import org.sodeja.runtime.scheme.model.Combination;
import org.sodeja.runtime.scheme.model.Symbol;

public class DefineForm extends SchemeForm {
	@Override
	protected Object evalDelegate(Evaluator<SchemeExpression> evaluator,
			SchemeFrame frame, Combination expression) {
		
		SchemeExpression exp = expression.get(1);
		if(exp instanceof Symbol) {
			return defineSymbol(evaluator, frame, (Symbol) exp, Utils.extractParts(expression));
		}
		
		if(exp instanceof Combination) {
			return defineLambda(evaluator, frame, (Combination) exp, Utils.extractParts(expression));
		}
		
		throw new IllegalArgumentException();
	}

	private Object defineSymbol(Evaluator<SchemeExpression> evaluator,
			SchemeFrame frame, Symbol symbol,
			List<SchemeExpression> extractParts) {
		
		frame.addObject(symbol, Utils.evalSingle(evaluator, frame, extractParts));
		return symbol;
	}

	private Object defineLambda(Evaluator<SchemeExpression> evaluator,
			SchemeFrame frame, Combination exp,
			List<SchemeExpression> extractParts) {
		
		Symbol symbol = (Symbol) exp.get(0);
		List<Symbol> params = LambdaForm.mapTokenToSymbol(ListUtils.tail(exp));
		frame.addObject(symbol, new CompoundProcedure(evaluator, frame, params, extractParts));
		return symbol;
	}
}
