package org.sodeja.interpret;

public interface Evaluator<T extends Token, F extends Frame<T>> {
	Object eval(F frame, T token);
}
