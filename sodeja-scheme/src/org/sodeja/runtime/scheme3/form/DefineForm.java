package org.sodeja.runtime.scheme3.form;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.runtime.compiler.CompilingDialect;
import org.sodeja.runtime.compiler.CompilingForm;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.model.Combination;
import org.sodeja.runtime.scheme.model.Symbol;
import org.sodeja.runtime.scheme3.CompiledSchemeDialect;
import org.sodeja.runtime.scheme3.CompiledSchemeExpression;
import org.sodeja.runtime.scheme3.DefineExpression;
import org.sodeja.runtime.scheme3.LambdaExpression;
import org.sodeja.runtime.scheme3.NameExpression;

public class DefineForm implements CompilingForm<SchemeExpression, CompiledSchemeExpression> {
	@Override
	public CompiledSchemeExpression compile(
			CompilingDialect<SchemeExpression, CompiledSchemeExpression> dialect, SchemeExpression expression) {
		
		Combination combination = (Combination) expression;
		SchemeExpression nameExpression = combination.get(1);
		
		List<SchemeExpression> values = combination.subList(2, combination.size());
		
		if(nameExpression instanceof Symbol) {
			List<CompiledSchemeExpression> compiledValues = dialect.compileList(values);
			return compileNameDefinition((Symbol) nameExpression, compiledValues);
		}
		
		return compileLambdaDefinition(dialect, (Combination) nameExpression, values);
	}

	private CompiledSchemeExpression compileNameDefinition(Symbol nameExpression,
			List<CompiledSchemeExpression> values) {
		return new DefineExpression(new NameExpression(nameExpression), values);
	}

	private CompiledSchemeExpression compileLambdaDefinition(
			CompilingDialect<SchemeExpression, CompiledSchemeExpression> dialect,
			Combination nameExpression, List<SchemeExpression> body) {
		
		List<NameExpression> nameAndParams = LambdaForm.getFormalParameters(nameExpression);
		List<NameExpression> params = nameAndParams.subList(1, nameAndParams.size());
		
		((CompiledSchemeDialect) dialect).addScope(params);
		List<CompiledSchemeExpression> compiledBody = dialect.compileList(body);
		
		return new DefineExpression(ListUtils.head(nameAndParams), new LambdaExpression(params, compiledBody));
	}
}
