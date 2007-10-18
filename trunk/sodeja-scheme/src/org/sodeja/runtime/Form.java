package org.sodeja.runtime;

public interface Form<E extends Expression> {
	Object eval(Evaluator<E> runtime, Frame<E> frame, E expression);
}
