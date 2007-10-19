package org.sodeja.runtime.scheme2.form;

import java.util.List;

import org.sodeja.runtime.compiler.CompilingDialect;
import org.sodeja.runtime.compiler.CompilingForm;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.model.Combination;
import org.sodeja.runtime.scheme2.BeginExpression;
import org.sodeja.runtime.scheme2.CompiledSchemeExpression;

public class BeginForm implements CompilingForm<SchemeExpression, CompiledSchemeExpression> {

	@Override
	public CompiledSchemeExpression compile(
			CompilingDialect<SchemeExpression, CompiledSchemeExpression> dialect,
			SchemeExpression expression) {

		Combination comb = (Combination) expression;
		List<SchemeExpression> contents = comb.subList(1, comb.size());
		return new BeginExpression(dialect.compileList(contents));
	}
}
