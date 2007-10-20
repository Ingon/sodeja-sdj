package org.sodeja.runtime.scheme3.form;

import org.sodeja.runtime.compiler.CompilingDialect;
import org.sodeja.runtime.compiler.CompilingForm;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.model.Combination;
import org.sodeja.runtime.scheme3.CompiledSchemeExpression;
import org.sodeja.runtime.scheme3.IfExpression;

public class IfForm implements CompilingForm<SchemeExpression, CompiledSchemeExpression> {
	@Override
	public CompiledSchemeExpression compile(
			CompilingDialect<SchemeExpression, CompiledSchemeExpression> dialect, SchemeExpression expression) {

		Combination combination = (Combination) expression;
		
		CompiledSchemeExpression predicate = dialect.compile(combination.get(1));
		CompiledSchemeExpression consequent = dialect.compile(combination.get(2));
		CompiledSchemeExpression alternative = dialect.compile(combination.get(3));
		
		return new IfExpression(predicate, consequent, alternative);
	}
}
