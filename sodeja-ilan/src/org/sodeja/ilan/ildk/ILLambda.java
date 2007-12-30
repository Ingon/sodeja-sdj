package org.sodeja.ilan.ildk;

import java.util.List;

import org.sodeja.ilan.parser.Expression;
import org.sodeja.ilan.runtime.IContext;

public class ILLambda extends ILObject {
	public final IContext context;
	public final List<ILSymbol> params;
	public final List<Expression> expressions;
	
	public ILLambda(IContext context, List<ILSymbol> params, List<Expression> expressions) {
		this.context = context;
		this.params = params;
		this.expressions = expressions;
	}
	
	public ILObject apply(List<ILObject> arguments) {
		IContext newContext = new IContext(context);
		if(params.size() != arguments.size()) {
			throw new IllegalArgumentException("Wrong number of arguments");
		}
		
		for(int i = 0, n = params.size();i < n;i++) {
			newContext.def(params.get(i), arguments.get(i));
		}
		
		ILObject result = null;
		for(Expression expr : expressions) {
			result = expr.eval(newContext);
		}
		return result;
	}
}
