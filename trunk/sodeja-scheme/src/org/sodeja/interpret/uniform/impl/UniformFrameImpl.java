package org.sodeja.interpret.uniform.impl;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.interpret.Token;
import org.sodeja.interpret.uniform.UniformFrame;

public class UniformFrameImpl<T extends Token> implements UniformFrame<T> {

	private final UniformFrame<T> parent;
	private final Map<T, Object> objects;
	
	private UniformFrameImpl() {
		this.parent = null;
		this.objects = null;
	}
	
	public UniformFrameImpl(UniformFrame<T> parent) {
		if(parent == null) {
			this.parent = new NullUniformFrame<T>();
		} else {
			this.parent = parent;
		}
		objects = new HashMap<T, Object>();
	}
	
	@Override
	public Object findObject(T symbol) {
		Object value = objects.get(symbol);
		if(value != null) {
			return value;
		}
		
		return parent.findObject(symbol);
	}
	
	@Override
	public void addObject(T symbol, Object value) {
		if(objects.containsKey(symbol)) {
			throw new IllegalArgumentException("Not possible to redefine names in this context");
		}
		objects.put(symbol, value);
	}

	@Override
	public void setObject(T symbol, Object value) {
		if(! objects.containsKey(symbol)) {
			parent.setObject(symbol, value);
			return;
		}
		objects.put(symbol, value);
	}
	
	private class NullUniformFrame<D extends Token> extends UniformFrameImpl<D> {
		@Override
		public Object findObject(D token) {
			throw new IllegalArgumentException("Symbol '" + token + "' does not exists!");
		}

		@Override
		public void addObject(D token, Object value) {
		}

		@Override
		public void setObject(D token, Object value) {
			throw new IllegalArgumentException("Symbol '" + token + "' does not exists!");
		}
	}
}
