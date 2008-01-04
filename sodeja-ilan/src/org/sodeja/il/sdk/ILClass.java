package org.sodeja.il.sdk;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.il.runtime.SDK;

public class ILClass implements ILObject {
	protected final ILSymbol name;
	protected final ILClass parent;
	
	protected ILClassLambda constructor;
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
		if(this.name.equals(name)) {
			if(constructor != null) {
				throw new UnsupportedOperationException();
			}
			
			constructor = value;
			return;
		}
		methods.put(name, value);
	}
	
	public ILClassLambda getConstructorLambda() {
		return constructor;
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
