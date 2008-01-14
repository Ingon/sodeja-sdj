package org.sodeja.sil.runtime.exec;

import java.util.HashMap;
import java.util.Map;

public class SILObjectClass implements SILClass {

	private final Map<String, Method> methods;
	
	public SILObjectClass() {
		methods = new HashMap<String, Method>();
	}

	@Override
	public Method findMethod(String selector) {
		return methods.get(selector);
	}

	@Override
	public SILClass getClass_() {
		throw new UnsupportedOperationException();
	}
}
