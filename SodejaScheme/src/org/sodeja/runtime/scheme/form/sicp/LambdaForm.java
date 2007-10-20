package org.sodeja.runtime.scheme.form.sicp;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.runtime.Evaluator;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.SchemeForm;
import org.sodeja.runtime.scheme.SchemeFrame;
import org.sodeja.runtime.scheme.Utils;
import org.sodeja.runtime.scheme.model.Combination;
import org.sodeja.runtime.scheme.model.Symbol;

public class LambdaForm extends SchemeForm {
	@Override
	protected Object evalDelegate(Evaluator<SchemeExpression> runtime,
			SchemeFrame frame, Combination expression) {
		
		Combination paramsComb = (Combination) expression.get(1);
		List<SchemeExpression> parts = Utils.extractParts(expression);
		return new CompoundProcedure(runtime, frame, getParams(paramsComb), parts);
	}
	
	private List<Symbol> getParams(Combination params) {
		return mapTokenToSymbol(params);
	}
	
	protected static List<Symbol> mapTokenToSymbol(List<SchemeExpression> tokens) {
		return ListUtils.map(tokens, new Function1<Symbol, SchemeExpression>() {
			@Override
			public Symbol execute(SchemeExpression p) {
				return (Symbol) p;
			}});
	}
}
