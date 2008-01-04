package org.sodeja.il.runtime;

import java.util.List;

import org.sodeja.il.model.Expression;

public class ILDefaultClassLambda implements ILClassLambda {
	private final Context context;
	private final List<ILSymbol> arguments;
	private final Expression body;
	
	public ILDefaultClassLambda(Context context, List<ILSymbol> arguments, Expression body) {
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
