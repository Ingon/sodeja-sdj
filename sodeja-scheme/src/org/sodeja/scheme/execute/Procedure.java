package org.sodeja.scheme.execute;

public interface Procedure extends Executable {
	public Object execute(Object... vals);
}
