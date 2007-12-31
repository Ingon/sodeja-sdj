package org.sodeja.ilan.parser;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.ilan.ildk.ILLambda;
import org.sodeja.ilan.ildk.ILObject;
import org.sodeja.ilan.ildk.ILSymbol;
import org.sodeja.ilan.runtime.Context;
import org.sodeja.ilan.runtime.ObjectContext;

public class ApplyExpression implements Expression {

	public final List<Expression> expressions;
	
	public ApplyExpression(List<Expression> expressions) {
		this.expressions = expressions;
	}

	@Override
	public ILObject eval(final Context context) {
		Expression expr = expressions.get(0);
		ILObject obj = expr.eval(context);
		if(obj instanceof ILLambda) {
			List<ILObject> params = eval(context, ListUtils.tail(expressions));
			return ((ILLambda) obj).apply(params);
		}
		
		Expression methodExpr = expressions.get(1);
		if(!(methodExpr instanceof VariableExpression)) {
			throw new RuntimeException("When calling method second should be a symbol");
		}
		
		ILSymbol methodName = (ILSymbol) methodExpr.eval(context);
		ILLambda method = obj.getILClass().getMethod(methodName);
		
		List<ILObject> params = eval(context, expressions.subList(2, expressions.size()));
		
		Context newContext = new ObjectContext(context);
		return method.apply(params);
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
