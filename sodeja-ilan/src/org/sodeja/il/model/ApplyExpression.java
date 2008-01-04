package org.sodeja.il.model;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.il.runtime.Context;
import org.sodeja.il.sdk.ILClass;
import org.sodeja.il.sdk.ILClassLambda;
import org.sodeja.il.sdk.ILFreeLambda;
import org.sodeja.il.sdk.ILObject;

public class ApplyExpression implements Expression {
	public final List<Expression> expressions;

	public ApplyExpression(List<Expression> expressions) {
		this.expressions = expressions;
	}

	@Override
	public ILObject eval(final Context ctx) {
		ILObject value = ListUtils.head(expressions).eval(ctx);
		if(value instanceof ILFreeLambda) {
			return applyLambda(ctx, (ILFreeLambda) value, ListUtils.tail(expressions));
		}
		
		return applyMethod(ctx, value, ListUtils.tail(expressions));
	}

	private ILObject applyLambda(Context ctx, ILFreeLambda lambda, List<Expression> tail) {
		return lambda.apply(eval(ctx, tail));
	}
	
	private ILObject applyMethod(Context ctx, ILObject value, List<Expression> tail) {
		if(tail.isEmpty()) {
			return value;
		}
		
		if(! (ListUtils.head(tail) instanceof VariableExpression)) {
			throw new RuntimeException("Should be a variable");
		}
		
		VariableExpression varName = (VariableExpression) ListUtils.head(tail);
		
		ILClass type = value.getType();
		ILClassLambda lambda = type.getLambda(varName.name);
		return lambda.applyObject(value, eval(ctx, ListUtils.tail(tail)));
	}
	
	private List<ILObject> eval(final Context ctx, List<Expression> exprs) {
		return ListUtils.map(exprs, new Function1<ILObject, Expression>() {
			@Override
			public ILObject execute(Expression p) {
				return p.eval(ctx);
			}});
	}
}
