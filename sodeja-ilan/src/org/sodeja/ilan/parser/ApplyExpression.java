package org.sodeja.ilan.parser;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.ilan.ildk.ILLambda;
import org.sodeja.ilan.ildk.ILObject;
import org.sodeja.ilan.runtime.Context;

public class ApplyExpression implements Expression {

	public final List<Expression> expressions;
	
	public ApplyExpression(List<Expression> expressions) {
		this.expressions = expressions;
	}

	@Override
	public ILObject eval(final Context context) {
		Expression expr = expressions.get(0);
		ILObject obj = expr.eval(context);
		if(! (obj instanceof ILLambda)) {
			throw new IllegalArgumentException(expr + " does not evals to a function!");
		}
		
		List<ILObject> params = ListUtils.map(ListUtils.tail(expressions), new Function1<ILObject, Expression>() {
			@Override
			public ILObject execute(Expression p) {
				return p.eval(context);
			}});
		
		return ((ILLambda) obj).apply(params);
	}

	@Override
	public String toString() {
		return expressions.toString();
	}
}
