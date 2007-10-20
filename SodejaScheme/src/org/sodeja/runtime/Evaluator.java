package org.sodeja.runtime;

public interface Evaluator<E extends Expression> {
	Object eval(Frame<E> frame, E expression);
}
