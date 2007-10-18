package org.sodeja.runtime;

public interface Frame<E extends Expression> {
	Object findObject(E expression);
	void addObject(E expression, Object value);
	void setObject(E expression, Object value);
}
