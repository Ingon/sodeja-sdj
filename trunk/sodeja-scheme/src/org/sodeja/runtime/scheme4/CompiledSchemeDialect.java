package org.sodeja.runtime.scheme4;

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
import org.sodeja.runtime.scheme4.form.BeginForm;
import org.sodeja.runtime.scheme4.form.CondForm;
import org.sodeja.runtime.scheme4.form.DefineForm;
import org.sodeja.runtime.scheme4.form.IfForm;
import org.sodeja.runtime.scheme4.form.LambdaForm;
import org.sodeja.runtime.scheme4.form.LetForm;
import org.sodeja.runtime.scheme4.form.QuoteForm;
import org.sodeja.runtime.scheme4.form.SetForm;

public class CompiledSchemeDialect implements CompilingDialect<SchemeExpression, CompiledSchemeExpression> {
	
	private final Map<Symbol, CompilingForm<SchemeExpression, CompiledSchemeExpression>> forms;
	private Scope currentScope;
	
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
	
	private CompiledSchemeExpression compileSymbol(Symbol symbol) {
		try {
			return new ValueExpression<Rational>(new Rational(symbol.value));
		} catch(NumberFormatException exc) {
			return createVariable(symbol);
		}
	}

	private CompiledSchemeExpression createVariable(Symbol symbol) {
		NameExpression nameExpression = new NameExpression(symbol);
		if(currentScope == null) {
			return new FreeVariableExpression(nameExpression);
		}
		
		int scopeIndex = currentScope.find(nameExpression);
		if(scopeIndex < 0) {
			return new FreeVariableExpression(nameExpression);
		}
		
		return new BoundVariableExpression(nameExpression, scopeIndex);
	}

	private CompiledSchemeExpression compileCombination(Combination expression) {
		CompilingForm<SchemeExpression, CompiledSchemeExpression> form = forms.get(expression.getFirst());
		if(form != null) {
			return form.compile(this, expression);
		}
		
		List<CompiledSchemeExpression> compiled = compileList(expression);
		return new ApplicationExpression(ListUtils.head(compiled), ListUtils.tail(compiled));
	}
	
	public Scope addScope(List<NameExpression> expressions) {
		if(currentScope == null) {
			currentScope = new Scope(expressions);
		} else {
			currentScope = new Scope(currentScope, expressions);
		}
		
		return currentScope;
	}
	
	public Scope currentScope() {
		return currentScope;
	}
	
	public void removeScope() {
		currentScope = currentScope.getParent();
	}
}
