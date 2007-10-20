package org.sodeja.scheme.execute.form;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.scheme.execute.Frame;
import org.sodeja.scheme.parse.model.Expression;
import org.sodeja.scheme.parse.model.SExpression;

public class CondForm implements Form {
	@Override
	public Object execute(Frame frame, List<Expression> expressions) {
		for(Expression exp : expressions) {
			if(! (exp instanceof SExpression)) {
				throw new IllegalArgumentException("Clauses should be s-expressions");
			}
			
			SExpression clauseExp = (SExpression) exp;
			if(clauseExp.expressions.size() < 2) {
				throw new IllegalArgumentException("Clause expression contains predicate and at least one action");
			}
			
			Expression predicate = ListUtils.head(clauseExp.expressions);
			Object obj = frame.eval(predicate);
			if(! (obj instanceof Boolean)) {
				throw new IllegalArgumentException("Predicate does not returns boolean!");
			}
			
			if(! ((Boolean) obj).booleanValue()) {
				continue;
			}

			Object val = null;
			for(Expression resultExp : ListUtils.tail(clauseExp.expressions)) {
				val = frame.eval(resultExp);
			}
			return val;
		}
		
		throw new RuntimeException("No matching clause was found!");
	}
}
