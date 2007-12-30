package org.sodeja.ilan.parser;

import org.sodeja.ilan.ildk.ILBoolean;
import org.sodeja.ilan.ildk.ILObject;
import org.sodeja.ilan.runtime.Context;

public class IfExpression implements Expression {

	public final Expression predicate;
	public final Expression consequent;
	public final Expression alternative;
	
	public IfExpression(Expression predicate, Expression consequent, Expression alternative) {
		this.predicate = predicate;
		this.consequent = consequent;
		this.alternative = alternative;
	}

	@Override
	public ILObject eval(Context context) {
		ILObject obj = predicate.eval(context);
		if(! (obj instanceof ILBoolean)) {
			throw new RuntimeException("Predicate should evaluates to a boolean");
		}
		
		if(((ILBoolean) obj).isTrue()) {
			return consequent.eval(context);
		}
		
		return alternative.eval(context);
	}
}
