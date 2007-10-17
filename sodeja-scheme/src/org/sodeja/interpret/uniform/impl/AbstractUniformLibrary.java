package org.sodeja.interpret.uniform.impl;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.interpret.Token;
import org.sodeja.interpret.uniform.UniformLibrary;

public abstract class AbstractUniformLibrary<T extends Token> implements UniformLibrary<T> {

	private final Map<T, Object> mapping;
	
	public AbstractUniformLibrary() {
		mapping = new HashMap<T, Object>();
		init(mapping);
	}
	
	protected abstract void init(Map<T, Object> mapping);
	
	@Override
	public Object findObject(T symbol) {
		return mapping.get(symbol);
	}

}
