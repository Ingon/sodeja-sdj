package org.sodeja.explicit2;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.model.Combination;
import org.sodeja.runtime.scheme.model.Symbol;

public class Compiler {
	
	private LexicalScope currentScope;
	
	public CompiledExpression compile(SchemeExpression expr) {
		if(expr instanceof Symbol) {
			return compileSymbol((Symbol) expr);
		}
		
		return compileCombination((Combination) expr);
	}

	private CompiledExpression compileSymbol(final Symbol sym) {
		try {
			return new Rational(new org.sodeja.math.Rational(sym.value));
		} catch(NumberFormatException exc) {
			return compileVariable(sym);
		}
	}
	
	private CompiledExpression compileVariable(final Symbol sym) {
		if(currentScope == null) {
			return new Variable(sym);
		}
		
		int lexicalIndex = currentScope.find(sym);
		if(lexicalIndex < 0) {
			return new Variable(sym);
		}
		
		return new LexicalVariable(sym, lexicalIndex);
	}

	private CompiledExpression compileCombination(Combination expr) {
		SchemeExpression type = expr.get(0);
		if(type instanceof Combination) {
			return compileApplication(expr);
		}
		
		Symbol sym = (Symbol) type;
		String val = sym.value;
		
		if(val.equals("define")) {
			return compileDefinition(expr);
		}
		
		if(val.equals("lambda")) {
			return compileLambda(expr);
		}
		
		if(val.equals("if")) {
			return compileIf(expr);
		}

		if(val.equals("begin")) {
			throw new UnsupportedOperationException();
		}

		if(val.equals("set!")) {
			throw new UnsupportedOperationException();
		}

		if(val.equals("quote")) {
			throw new UnsupportedOperationException();
		}

		return compileApplication(expr);
	}

	private CompiledExpression compileApplication(Combination expr) {
		CompiledExpression proc = compile(expr.get(0));
		List<CompiledExpression> args = ListUtils.map(expr.subList(1, expr.size()), new Function1<CompiledExpression, SchemeExpression>() {
			@Override
			public CompiledExpression execute(SchemeExpression p) {
				return compile(p);
			}});
		return new Application(proc, args);
	}

	private CompiledExpression compileDefinition(Combination comb) {
		SchemeExpression nameExpr = comb.get(1);
		if(nameExpr instanceof Symbol) {
			return new Define((Symbol) nameExpr, compile(comb.get(2)));
		}
		
		Combination nameAndLambdaParams = (Combination) nameExpr;
		Combination lambdaParams = new Combination();
		lambdaParams.addAll(nameAndLambdaParams.subList(1, nameAndLambdaParams.size()));
		
		Combination lambda = new Combination();
		lambda.add(new Symbol("lambda"));
		lambda.add(lambdaParams);
		lambda.addAll(comb.subList(2, comb.size()));

		return new Define((Symbol) nameAndLambdaParams.get(0), compile(lambda));
	}

	private CompiledExpression compileLambda(Combination comb) {
		Combination paramsComb = (Combination) comb.get(1);
		List<Symbol> params = ListUtils.map(paramsComb, new Function1<Symbol, SchemeExpression>() {
			@Override
			public Symbol execute(SchemeExpression p) {
				return (Symbol) p;
			}});
		
		List<SchemeExpression> bodyExpr = comb.subList(2, comb.size());
		
		addScope(params);
		List<CompiledExpression> body = ListUtils.map(bodyExpr, new Function1<CompiledExpression, SchemeExpression>() {
			@Override
			public CompiledExpression execute(SchemeExpression p) {
				return compile(p);
			}});
		clearScope();
		
		return new Lambda(params, body);
	}

	private void addScope(List<Symbol> vars) {
		if(currentScope == null) {
			currentScope = new LexicalScope(vars);
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	private void clearScope() {
		currentScope = currentScope.getParent();
	}
	
	private CompiledExpression compileIf(Combination expr) {
		CompiledExpression predicate = compile(expr.get(1));
		CompiledExpression consequent = compile(expr.get(2));
		CompiledExpression alternative = compile(expr.get(3));
		return new If(predicate, consequent, alternative);
	}
}
