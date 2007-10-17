package org.sodeja.interpret;

import java.util.List;

public interface Form<T extends Token, F extends Frame<T>> {
	Object eval(Evaluator<T, F> runtime, F frame, List<T> token);
}
