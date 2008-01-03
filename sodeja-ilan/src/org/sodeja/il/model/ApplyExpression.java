package org.sodeja.il.model;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.il.runtime.Context;
import org.sodeja.il.runtime.ILLambda;
import org.sodeja.il.runtime.ILObject;

public class ApplyExpression implements Expression {
	public final List<Expression> expressions;

	public ApplyExpression(List<Expression> expressions) {
		this.expressions = expressions;
	}

	@Override
	public ILObject eval(final Context ctx) {
		Object value = ListUtils.head(expressions).eval(ctx);
		if(value instanceof ILLambda) {
			return applyLambda(ctx, (ILLambda) value, ListUtils.tail(expressions));
		}
		
		return applyMethod(ctx, value, ListUtils.tail(expressions));
	}

	private ILObject applyLambda(Context ctx, ILLambda value, List<Expression> tail) {
		throw new UnsupportedOperationException();
	}
	
	private ILObject applyMethod(Context ctx, Object value, List<Expression> tail) {
		throw new UnsupportedOperationException();
	}
	
	private List<ILObject> eval(final Context ctx, List<Expression> exprs) {
		return ListUtils.map(exprs, new Function1<ILObject, Expression>() {
			@Override
			public ILObject execute(Expression p) {
				return p.eval(ctx);
			}});
	}
}
