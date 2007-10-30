package org.sodeja.explicit2;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.math.Rational;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.model.Combination;
import org.sodeja.runtime.scheme.model.Symbol;

public class Compiler {
	public CompiledExpression compile(SchemeExpression expr) {
		if(expr instanceof Symbol) {
			return compileSymbol((Symbol) expr);
		}
		
		return compileCombination((Combination) expr);
	}

	private CompiledExpression compileSymbol(final Symbol sym) {
		try {
			final Rational rat = new Rational(sym.value);
			return new CompiledExpression() {
				@Override
				public void eval(Machine machine) {
					machine.val.setValue(rat);
					machine.exp.setValue(machine.cont.getValue());
				}
			};
		} catch(NumberFormatException exc) {
			return new CompiledExpression() {
				@Override
				public void eval(Machine machine) {
					Enviroment env = machine.env.getValue();
					machine.val.setValue(env.lookup(sym));
					machine.exp.setValue(machine.cont.getValue());
				}
			};
		}
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
		
		throw new UnsupportedOperationException();
	}

	private CompiledExpression compileApplication(Combination expr) {
		// TODO Auto-generated method stub
		return null;
	}

	private CompiledExpression compileDefinition(Combination expr) {
		final Symbol name = (Symbol) expr.get(1);
		final CompiledExpression value = compile(expr.get(2));
		return new CompiledExpression() {
			@Override
			public void eval(Machine machine) {
				machine.unev.setValue(ListUtils.asList((CompiledExpression) new SymbolCompiledExpression(name)));
				
				machine.exp.setValue(value);
				
				machine.unev.save();
				
				machine.env.save();
				
				machine.cont.save();
				
				machine.cont.setValue(new CompiledExpression() {
					@Override
					public void eval(Machine machine) {
						machine.cont.restore();
						machine.env.restore();
						machine.unev.restore();
						
						Enviroment env = machine.env.getValue();
						
						List<CompiledExpression> une = machine.unev.getValue();
						env.define(((SymbolCompiledExpression) une.get(0)).sym, machine.val.getValue());
						
						machine.val.setValue("ok");
						
						machine.exp.setValue(machine.cont.getValue());
					}});
			}};
	}
	
	private class SymbolCompiledExpression implements CompiledExpression {
		final Symbol sym;
		
		public SymbolCompiledExpression(Symbol sym) {
			this.sym = sym;
		}
		
		@Override
		public void eval(Machine machine) {
		}}
}
