package org.sodeja.il.runtime;

public interface Context {
	public void define(String name, Object value);
	public Object get(String name);
}
