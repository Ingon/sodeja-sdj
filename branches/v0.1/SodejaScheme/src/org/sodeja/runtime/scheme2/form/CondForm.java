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
import org.sodeja.runtime.scheme2.CondExpression;
import org.sodeja.runtime.scheme2.ValueExpression;

public class CondForm implements CompilingForm<SchemeExpression, CompiledSchemeExpression> {
	@Override
	public CompiledSchemeExpression compile(
			CompilingDialect<SchemeExpression, CompiledSchemeExpression> dialect, SchemeExpression expression) {
		
		List<Pair<CompiledSchemeExpression, List<CompiledSchemeExpression>>> clauses = 
			new ArrayList<Pair<CompiledSchemeExpression,List<CompiledSchemeExpression>>>();
		
		Combination combination = (Combination) expression;
		for(int i = 1, n = combination.size();i < n;i++) {
			SchemeExpression clauseExp = combination.get(i);
			if(! (clauseExp instanceof Combination)) {
				throw new IllegalArgumentException("Every clause has (<predicate> <action>) form!");
			}
			
			Combination clause = (Combination) clauseExp;
			if(clause.size() != 2) {
				throw new IllegalArgumentException("Every clause has (<predicate> <action>) form!");
			}
			
			CompiledSchemeExpression predicate = null;
			if(clause.get(0) instanceof Symbol && ((Symbol) clause.get(0)).value.equals("else")) {
				predicate = new ValueExpression<Boolean>(Boolean.TRUE);
			} else {
				predicate = dialect.compile(clause.get(0));
			}
			List<CompiledSchemeExpression> consequent = dialect.compileList(clause.subList(1, clause.size()));
			
			clauses.add(new Pair<CompiledSchemeExpression, List<CompiledSchemeExpression>>(predicate, consequent));
		}
		
		return new CondExpression(clauses);
	}
}
