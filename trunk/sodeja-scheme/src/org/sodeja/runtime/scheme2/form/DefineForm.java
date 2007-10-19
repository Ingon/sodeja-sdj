package org.sodeja.runtime.scheme2.form;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.runtime.compiler.CompilingDialect;
import org.sodeja.runtime.compiler.CompilingForm;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.model.Combination;
import org.sodeja.runtime.scheme.model.Symbol;
import org.sodeja.runtime.scheme2.CompiledSchemeExpression;
import org.sodeja.runtime.scheme2.DefineExpression;
import org.sodeja.runtime.scheme2.LambdaExpression;
import org.sodeja.runtime.scheme2.NameExpression;

public class DefineForm implements CompilingForm<SchemeExpression, CompiledSchemeExpression> {
	@Override
	public CompiledSchemeExpression compile(
			CompilingDialect<SchemeExpression, CompiledSchemeExpression> dialect, SchemeExpression expression) {
		
		Combination combination = (Combination) expression;
		SchemeExpression nameExpression = combination.get(1);
		
		List<SchemeExpression> values = combination.subList(2, combination.size());
		List<CompiledSchemeExpression> compiledValues = dialect.compileList(values);
		
		if(nameExpression instanceof Symbol) {
			return compileNameDefinition((Symbol) nameExpression, compiledValues);
		}
		
		return compileLambdaDefinition((Combination) nameExpression, compiledValues);
	}

	private CompiledSchemeExpression compileNameDefinition(Symbol nameExpression,
			List<CompiledSchemeExpression> values) {
		return new DefineExpression(new NameExpression(nameExpression), values);
	}

	private CompiledSchemeExpression compileLambdaDefinition(Combination nameExpression,
			List<CompiledSchemeExpression> body) {
		
		List<NameExpression> nameAndParams = LambdaForm.getParams(nameExpression);
		List<NameExpression> params = nameAndParams.subList(1, nameAndParams.size());
		
		return new DefineExpression(ListUtils.head(nameAndParams), new LambdaExpression(params, body));
	}
}
