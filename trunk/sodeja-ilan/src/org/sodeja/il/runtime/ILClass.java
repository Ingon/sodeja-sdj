package org.sodeja.il.runtime;

import java.util.HashMap;
import java.util.Map;

public class ILClass implements ILObject {
	protected final ILSymbol name;
	protected final ILClass parent;
	protected final Map<ILSymbol, ILClassLambda> methods;
	
	public ILClass(ILSymbol name, ILClass parent) {
		this.name = name;
		this.parent = parent;
		this.methods = new HashMap<ILSymbol, ILClassLambda>();
	}
	
	public ILObject makeInstance(Object... values) {
		throw new UnsupportedOperationException();
	}

	public void defineLambda(ILSymbol name, ILClassLambda value) {
		methods.put(name, value);
	}
	
	public ILClassLambda getLambda(ILSymbol symbol) {
		ILClassLambda lambda = methods.get(symbol);
		if(lambda != null) {
			return lambda;
		}
		
		if(parent != null) {
			lambda = parent.getLambda(symbol);
			if(lambda != null) {
				return lambda;
			}
		}
		
		return getMethodMissing(symbol);
	}
	
	protected ILClassLambda getMethodMissing(ILSymbol symbol) {
		ILClassLambda lambda = methods.get(ILNotFoundLambda.NAME);
		if(lambda != null) {
			return lambda;
		}
		
		if(parent != null) {
			return parent.getMethodMissing(symbol);
		}
		
		return new ILNotFoundLambda(symbol);
	}

	@Override
	public ILClass getType() {
		return SDK.getInstance().getMetaType();
	}

	@Override
	public String toString() {
		return "<" + name + ">";
	}
}
