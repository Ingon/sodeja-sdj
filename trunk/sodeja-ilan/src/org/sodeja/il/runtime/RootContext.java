package org.sodeja.il.runtime;

public class RootContext extends AbstractContext {
	@Override
	public void define(String name, Object value) {
		Object tvalue = super.get(name);
		if(tvalue != null) {
			throw new RuntimeException("Redefine is not possible");
		}
		super.define(name, value);
	}

	@Override
	public Object get(String name) {
		Object temp = super.get(name);
		if(temp == null) {
			throw new RuntimeException("Not defined: " + name);
		}
		return temp;
	}
}
