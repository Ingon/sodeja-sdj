package org.sodeja.runtime.scheme2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.math.Rational;
import org.sodeja.runtime.compiler.CompilingDialect;
import org.sodeja.runtime.compiler.CompilingForm;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.model.Combination;
import org.sodeja.runtime.scheme.model.Symbol;
import org.sodeja.runtime.scheme2.form.BeginForm;
import org.sodeja.runtime.scheme2.form.CondForm;
import org.sodeja.runtime.scheme2.form.DefineForm;
import org.sodeja.runtime.scheme2.form.IfForm;
import org.sodeja.runtime.scheme2.form.LambdaForm;
import org.sodeja.runtime.scheme2.form.LetForm;
import org.sodeja.runtime.scheme2.form.QuoteForm;
import org.sodeja.runtime.scheme2.form.SetForm;

public class CompiledSchemeDialect implements CompilingDialect<SchemeExpression, CompiledSchemeExpression> {
	
	private final Map<Symbol, CompilingForm<SchemeExpression, CompiledSchemeExpression>> forms;
	
	public CompiledSchemeDialect() {
		forms = new HashMap<Symbol, CompilingForm<SchemeExpression,CompiledSchemeExpression>>();
		
		forms.put(new Symbol("define"), new DefineForm());
		forms.put(new Symbol("lambda"), new LambdaForm());

		forms.put(new Symbol("if"), new IfForm());
		forms.put(new Symbol("cond"), new CondForm());
		forms.put(new Symbol("let"), new LetForm());
		forms.put(new Symbol("quote"), new QuoteForm());
		forms.put(new Symbol("begin"), new BeginForm());
		forms.put(new Symbol("set!"), new SetForm());
	}
	
	@Override
	public CompiledSchemeExpression compile(SchemeExpression expression) {
		if(expression instanceof Symbol) {
			return compileSymbol((Symbol) expression);
		}
		
		if(expression instanceof Combination) {
			return compileCombination((Combination) expression);
		}
		
		throw new IllegalArgumentException("Unknown expression: " + expression);
	}

	@Override
	public List<CompiledSchemeExpression> compileList(List<SchemeExpression> expressions) {
		return ListUtils.map(expressions, new Function1<CompiledSchemeExpression, SchemeExpression>() {
			@Override
			public CompiledSchemeExpression execute(SchemeExpression p) {
				return compile(p);
			}});
	}
	
	private CompiledSchemeExpression compileSymbol(Symbol expression) {
		try {
			return new ValueExpression<Rational>(new Rational(expression.value));
		} catch(NumberFormatException exc) {
			return new VariableExpression(expression);
		}
	}

	private CompiledSchemeExpression compileCombination(Combination expression) {
		CompilingForm<SchemeExpression, CompiledSchemeExpression> form = forms.get(expression.getFirst());
		if(form != null) {
			return form.compile(this, expression);
		}
		
		List<CompiledSchemeExpression> compiled = compileList(expression);
		return new ApplicationExpression(ListUtils.head(compiled), ListUtils.tail(compiled));
	}
}
