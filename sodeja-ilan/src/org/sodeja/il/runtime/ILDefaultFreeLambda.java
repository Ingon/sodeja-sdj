package org.sodeja.il.runtime;

import java.util.List;

import org.sodeja.il.model.Expression;

public class ILDefaultFreeLambda implements ILFreeLambda {
	
	private final Context context;
	private final List<ILSymbol> arguments;
	private final Expression body;
	
	public ILDefaultFreeLambda(Context context, List<ILSymbol> arguments, Expression body) {
		this.context = context;
		this.arguments = arguments;
		this.body = body;
	}

	@Override
	public ILObject apply(List<ILObject> values) {
		return body.eval(new FunctionContext(context, arguments, values));
	}

	@Override
	public ILClass getType() {
		throw new UnsupportedOperationException();
	}
}
