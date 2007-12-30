package org.sodeja.ilan.parser;

import java.util.List;

import org.sodeja.ilan.ildk.ILLambda;
import org.sodeja.ilan.ildk.ILObject;
import org.sodeja.ilan.ildk.ILSymbol;
import org.sodeja.ilan.runtime.IContext;

public class LambdaExpression implements Expression {
	
	public final List<ILSymbol> params;
	public final List<Expression> body;
	
	public LambdaExpression(List<ILSymbol> params, List<Expression> body) {
		this.params = params;
		this.body = body;
	}

	@Override
	public ILObject eval(IContext context) {
		return new ILLambda(context, params, body);
	}

	@Override
	public String toString() {
		return "(\\ " + params + " " + body + ")";
	}
}
