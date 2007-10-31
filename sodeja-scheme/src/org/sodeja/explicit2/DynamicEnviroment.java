package org.sodeja.explicit2;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.runtime.scheme.model.Symbol;

class DynamicEnviroment {
	private Map<Symbol, Object> mapping;
	
	public DynamicEnviroment() {
		this.mapping = new HashMap<Symbol, Object>();
	}
	
	public void define(Symbol sym, Object value) {
		mapping.put(sym, value);
	}
	
	public void set(Symbol sym, Object value) {
		if(mapping.containsKey(sym)) {
			throw new IllegalArgumentException("Symbol '" + sym + "' not found!");
		}
		mapping.put(sym, value);
	}

	public Object lookup(Symbol sym) {
		Object result = mapping.get(sym);
		if(result == null) {
			throw new IllegalArgumentException("Symbol '" + sym + "' not found!");
		}
		return result;
	}
}
