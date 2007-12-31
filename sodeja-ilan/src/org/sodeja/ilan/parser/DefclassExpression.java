package org.sodeja.ilan.parser;

import java.util.List;

import org.sodeja.ilan.ildk.ILClass;
import org.sodeja.ilan.ildk.ILClassFactory;
import org.sodeja.ilan.ildk.ILObject;
import org.sodeja.ilan.ildk.ILObjectClass;
import org.sodeja.ilan.ildk.ILSymbol;
import org.sodeja.ilan.runtime.ChildContext;
import org.sodeja.ilan.runtime.Context;

public class DefclassExpression implements Expression {

	public final ILSymbol className;
	public final ILSymbol parentClass;
	public final List<Expression> news;
	public final List<Expression> methods;
	
	public DefclassExpression(ILSymbol className, ILSymbol parentClass, List<Expression> news, List<Expression> methods) {
		this.className = className;
		this.parentClass = parentClass;
		this.news = news;
		this.methods = methods;
	}

	@Override
	public ILObject eval(Context context) {
		if(ILClassFactory.getInstance().hasILClass(className)) {
			throw new UnsupportedOperationException();
		}
		
		if(parentClass != null) {
			throw new UnsupportedOperationException();
		}
		
		Context newContext = new ChildContext(context);
		for(Expression expr : methods) {
			expr.eval(newContext);
		}
		
		ILClass clazz = ILClassFactory.getInstance().makeILClass(className, ILObjectClass.getInstance());
		return clazz;
	}
}
