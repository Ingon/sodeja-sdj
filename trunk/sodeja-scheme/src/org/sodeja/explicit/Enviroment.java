package org.sodeja.explicit;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.runtime.scheme.model.Symbol;

class Enviroment {

	private final Map<Symbol, Object> mapping;
	private final Enviroment parent;
	
	public Enviroment() {
		this(new NullEnviroment());
	}
	
	public Enviroment(Enviroment parent) {
		this.mapping = new HashMap<Symbol, Object>();
		this.parent = parent;
	}
	
	public void define(Symbol sym, Object value) {
		mapping.put(sym, value);
	}
	
	public void set(Symbol sym, Object value) {
		// TODO maybe error check?
		mapping.put(sym, value);
	}

	public Object lookup(Symbol sym) {
		Object result = mapping.get(sym);
		if(result == null) {
			return parent.lookup(sym);
		}
		return result;
	}
	
	private static class NullEnviroment extends Enviroment {
		public NullEnviroment() {
			super(null);
		}

		@Override
		public void define(Symbol sym, Object value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Object lookup(Symbol sym) {
			throw new IllegalArgumentException("Symbol '" + sym + "' not found!");
		}
	}
}
