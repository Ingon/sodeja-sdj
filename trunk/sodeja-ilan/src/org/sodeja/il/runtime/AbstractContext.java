package org.sodeja.il.runtime;

import java.util.HashMap;
import java.util.Map;

class AbstractContext implements Context {

	private final Map<String, Object> values;
	
	public AbstractContext() {
		this.values = new HashMap<String, Object>();
	}

	@Override
	public void define(String name, Object value) {
		values.put(name, value);
	}

	@Override
	public Object get(String name) {
		return values.get(name);
	}
}
