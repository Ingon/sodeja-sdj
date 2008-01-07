package org.sodeja.il.sdk;

import java.util.List;

import org.sodeja.il.model.Expression;
import org.sodeja.il.runtime.ClassContext;
import org.sodeja.il.runtime.Context;
import org.sodeja.il.runtime.FunctionContext;
import org.sodeja.il.runtime.ObjectContext;

public class ILDefaultClassLambda implements ILClassLambda {
	private final ClassContext context;
	private final List<ILSymbol> arguments;
	private final Expression body;
	
	public ILDefaultClassLambda(ClassContext context, List<ILSymbol> arguments, Expression body) {
		this.context = context;
		this.arguments = arguments;
		this.body = body;
	}

	@Override
	public ILObject applyObject(ILObject value, List<ILObject> values) {
		Context newContext = new ObjectContext(context, value);
		return body.eval(new FunctionContext(newContext, arguments, values));
	}

	@Override
	public ILClass getType() {
		throw new UnsupportedOperationException();
	}
}
