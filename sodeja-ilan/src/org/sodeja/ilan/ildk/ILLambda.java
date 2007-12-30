package org.sodeja.ilan.ildk;

import java.util.List;

import org.sodeja.ilan.parser.Expression;
import org.sodeja.ilan.runtime.Context;

public class ILLambda extends ILObject {
	public final Context context;
	public final List<ILSymbol> params;
	public final List<Expression> expressions;
	
	public ILLambda(Context context, List<ILSymbol> params, List<Expression> expressions) {
		this.context = context;
		this.params = params;
		this.expressions = expressions;
	}
	
	public Object apply() {
		throw new UnsupportedOperationException();
	}
}
