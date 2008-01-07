package org.sodeja.il.sdk;

import java.util.List;

import org.sodeja.il.model.Expression;
import org.sodeja.il.runtime.Context;
import org.sodeja.il.runtime.FunctionContext;

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
		if(arguments.size() > values.size()) {
			List<ILSymbol> partialApplyArgumentsList = arguments.subList(0, values.size());
			List<ILSymbol> partialApplyRestList = arguments.subList(values.size(), arguments.size());
			return new ILDefaultFreeLambda(new FunctionContext(context, partialApplyArgumentsList, values), partialApplyRestList, body);
		}
		return body.eval(new FunctionContext(context, arguments, values));
	}

	@Override
	public int getArgumentsCount() {
		return arguments.size();
	}

	@Override
	public ILClass getType() {
		throw new UnsupportedOperationException();
	}
}
