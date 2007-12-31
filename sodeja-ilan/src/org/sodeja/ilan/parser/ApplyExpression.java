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
			throw new RuntimeException("Not a lambda");
		}
		
		List<ILObject> params = eval(context, ListUtils.tail(expressions));
		return ((ILLambda) obj).apply(params);
	}

	private static List<ILObject> eval(final Context context, List<Expression> expressions) {
		return ListUtils.map(ListUtils.tail(expressions), new Function1<ILObject, Expression>() {
			@Override
			public ILObject execute(Expression p) {
				return p.eval(context);
			}});
	}
	
	@Override
	public String toString() {
		return expressions.toString();
	}
}
