package org.sodeja.runtime.scheme2.form;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.functional.Pair;
import org.sodeja.runtime.compiler.CompilingDialect;
import org.sodeja.runtime.compiler.CompilingForm;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.model.Combination;
import org.sodeja.runtime.scheme.model.Symbol;
import org.sodeja.runtime.scheme2.CompiledSchemeExpression;
import org.sodeja.runtime.scheme2.LetExpression;
import org.sodeja.runtime.scheme2.NameExpression;

public class LetForm implements CompilingForm<SchemeExpression, CompiledSchemeExpression> {

	@Override
	public CompiledSchemeExpression compile(
			CompilingDialect<SchemeExpression, CompiledSchemeExpression> dialect,
			SchemeExpression expression) {

		Combination combination = (Combination) expression;
		if (combination.size() < 3) {
			throw new IllegalArgumentException(
					"Expect at least two expressions - var binding and and executed");
		}
		
		if(! (combination.get(1) instanceof Combination)) {
			throw new IllegalArgumentException("Var bingings part has form ((<var1> <exp1>) (<var1> <exp1>)...)");
		}
		
		Combination bindings = (Combination) combination.get(1);
		List<Pair<NameExpression, CompiledSchemeExpression>> bindingsList = 
			new ArrayList<Pair<NameExpression,CompiledSchemeExpression>>();
		for(SchemeExpression binding : bindings) {
			compileBinding(dialect, binding, bindingsList);
		}
		
		List<SchemeExpression> body = combination.subList(2, combination.size());
		return new LetExpression(bindingsList, dialect.compileList(body));
	}

	private void compileBinding(CompilingDialect<SchemeExpression, CompiledSchemeExpression> dialect,
			SchemeExpression bindingExpression, List<Pair<NameExpression, CompiledSchemeExpression>> bindingsList) {

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
		
		Symbol symbol = (Symbol) symbolExpression;
		bindingsList.add(Pair.of(new NameExpression(symbol), dialect.compile(bindingComb.get(1))));
	}
}
