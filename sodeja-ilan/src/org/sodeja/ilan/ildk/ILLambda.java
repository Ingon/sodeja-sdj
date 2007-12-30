package org.sodeja.ilan.ildk;

import java.util.List;

import org.sodeja.ilan.parser.Expression;
import org.sodeja.ilan.runtime.Context;
import org.sodeja.ilan.runtime.ChildContext;

public class ILLambda extends ILObject {
	public final Context context;
	public final List<ILSymbol> params;
	public final List<Expression> expressions;
	
	public ILLambda(Context context, List<ILSymbol> params, List<Expression> expressions) {
		this.context = context;
		this.params = params;
		this.expressions = expressions;
	}
	
	public ILObject apply(List<ILObject> arguments) {
		Context newContext = new ChildContext(context);
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
