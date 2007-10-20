package org.sodeja.runtime.scheme3.form;

import org.sodeja.runtime.compiler.CompilingDialect;
import org.sodeja.runtime.compiler.CompilingForm;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.model.Combination;
import org.sodeja.runtime.scheme3.CompiledSchemeExpression;
import org.sodeja.runtime.scheme3.ValueExpression;

public class QuoteForm implements CompilingForm<SchemeExpression, CompiledSchemeExpression>{
	@Override
	public CompiledSchemeExpression compile(
			CompilingDialect<SchemeExpression, CompiledSchemeExpression> dialect, SchemeExpression expression) {
		Combination comb = (Combination) expression;
		return new ValueExpression<Object>(comb.get(1));
	}
}
