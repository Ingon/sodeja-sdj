package org.sodeja.interpret;

public interface Dialect<T extends Token, F extends Frame<T>> {
	Form<T, F> find(T token);
}
