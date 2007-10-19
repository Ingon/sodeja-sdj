package org.sodeja.runtime.scheme3.form;

import org.sodeja.runtime.compiler.CompilingDialect;
import org.sodeja.runtime.compiler.CompilingForm;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.model.Combination;
import org.sodeja.runtime.scheme.model.Symbol;
import org.sodeja.runtime.scheme3.CompiledSchemeExpression;
import org.sodeja.runtime.scheme3.NameExpression;
import org.sodeja.runtime.scheme3.SetExpression;

public class SetForm implements CompilingForm<SchemeExpression, CompiledSchemeExpression> {
	@Override
	public CompiledSchemeExpression compile(
			CompilingDialect<SchemeExpression, CompiledSchemeExpression> dialect, SchemeExpression expression) {
		
		Combination combination = (Combination) expression;
		
		if(combination.size() != 3) {
			throw new IllegalArgumentException("Should give a variable and an expression!");
		}

		SchemeExpression varExpression = combination.get(1);
		if(! (varExpression instanceof Symbol)) {
			throw new IllegalArgumentException("Should give a variable and an expression!");
		}
		NameExpression var = new NameExpression((Symbol) varExpression);
		return new SetExpression(var, dialect.compile(combination.get(2)));
	}
}
