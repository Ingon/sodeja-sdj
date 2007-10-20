package org.sodeja.runtime.scheme2.form;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.runtime.compiler.CompilingDialect;
import org.sodeja.runtime.compiler.CompilingForm;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.model.Combination;
import org.sodeja.runtime.scheme.model.Symbol;
import org.sodeja.runtime.scheme2.CompiledSchemeExpression;
import org.sodeja.runtime.scheme2.LambdaExpression;
import org.sodeja.runtime.scheme2.NameExpression;

public class LambdaForm implements CompilingForm<SchemeExpression, CompiledSchemeExpression> {
	@Override
	public CompiledSchemeExpression compile(
			CompilingDialect<SchemeExpression, CompiledSchemeExpression> dialect, SchemeExpression expression) {
		
		Combination combination = (Combination) expression;
		SchemeExpression paramsExpression = combination.get(1);
		if(! (paramsExpression instanceof Combination)) {
			throw new IllegalArgumentException("Expecting arguments list");
		}
		
		Combination params = (Combination) paramsExpression;		
		List<SchemeExpression> bodyExpressions = combination.subList(2, combination.size());
		
		return new LambdaExpression(getParams(params), dialect.compileList(bodyExpressions));
	}
	
	static List<NameExpression> getParams(Combination params) {
		return getRealParams(params);
	}

	static List<NameExpression> getRealParams(List<SchemeExpression> params) {
		return ListUtils.map(params, new Function1<NameExpression, SchemeExpression>() {
			@Override
			public NameExpression execute(SchemeExpression p) {
				if(! (p instanceof Symbol)) {
					throw new IllegalArgumentException("Argument is symbol only");
				}
				return new NameExpression((Symbol) p);
			}});
	}
}
