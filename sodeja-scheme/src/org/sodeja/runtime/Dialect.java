package org.sodeja.runtime;

public interface Dialect<E extends Expression> {
	Form<E> find(E expression);
}
