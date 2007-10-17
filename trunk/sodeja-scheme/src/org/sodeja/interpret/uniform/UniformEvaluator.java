package org.sodeja.interpret.uniform;

import org.sodeja.interpret.Evaluator;
import org.sodeja.interpret.Token;

public interface UniformEvaluator<T extends Token> extends Evaluator<T, UniformFrame<T>> {
	Object eval(UniformFrame<T> frame, T token);
}
