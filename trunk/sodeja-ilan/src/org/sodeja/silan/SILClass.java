package org.sodeja.silan;

import java.util.HashMap;
import java.util.Map;

public class SILClass implements SILObject {
	
	private final SILClass superclass;
	private final Map<String, Method> methods;
	
	public SILClass(SILClass superclass) {
		this.superclass = superclass;
		this.methods = new HashMap<String, Method>();
	}
	
	protected void addMethod(String selector, Method method) {
		methods.put(selector, method);
	}
	
	public SILClass getSuperclass() {
		return superclass;
	}

	public Method findMethod(String selector) {
		return methods.get(selector);
	}

	@Override
	public SILClass getType() {
		throw new UnsupportedOperationException();
	}
}
