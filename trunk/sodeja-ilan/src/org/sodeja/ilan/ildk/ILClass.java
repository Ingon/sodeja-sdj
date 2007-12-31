package org.sodeja.ilan.ildk;

import java.util.HashMap;
import java.util.Map;

public class ILClass extends ILObject {
	public final ILSymbol name;
	public final ILClass parent;
	private final Map<ILSymbol, ILLambda> methods;
	
	protected ILClass(ILSymbol name, ILClass parent) {
		this.name = name;
		this.parent = parent;
		this.methods = new HashMap<ILSymbol, ILLambda>();
	}
	
	public ILLambda getMethod(ILSymbol symbol) {
		ILLambda lambda = methods.get(symbol);
		if(lambda != null) {
			return lambda;
		}
		
		return parent.getMethod(symbol);
	}
	
	public void defMethod(ILSymbol symbol, ILLambda lambda) {
		methods.put(symbol, lambda);
	}

	@Override
	protected ILSymbol getILClassName() {
		return new ILSymbol("ILClass");
	}
}
