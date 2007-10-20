package org.sodeja.runtime;

public interface Library<E extends Expression> {
	void extend(Frame<E> frame);
}
