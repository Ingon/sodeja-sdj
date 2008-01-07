package org.sodeja.il.model;

import org.sodeja.il.runtime.Context;
import org.sodeja.il.sdk.ILJavaObject;
import org.sodeja.il.sdk.ILObject;

public class IfExpression implements Expression {

	public final Expression predicate;
	public final Expression consequence;
	public final Expression alternative;
	
	public IfExpression(Expression predicate, Expression consequence, Expression alternative) {
		this.predicate = predicate;
		this.consequence = consequence;
		this.alternative = alternative;
	}

	@Override
	public ILObject eval(Context ctx) {
		ILObject obj = predicate.eval(ctx);
		if(! (obj instanceof ILJavaObject)) {
			throw new RuntimeException("Expected java object of type Boolean");
		}
		
		ILJavaObject<Boolean> tmp = (ILJavaObject<Boolean>) obj;
		if(tmp.getValue()) {
			return consequence.eval(ctx);
		} else if(alternative != null){
			return alternative.eval(ctx);
		} else {
			return null;
		}
	}
}
