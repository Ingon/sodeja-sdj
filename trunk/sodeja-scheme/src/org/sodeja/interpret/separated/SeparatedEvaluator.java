package org.sodeja.interpret.separated;

import org.sodeja.interpret.Evaluator;
import org.sodeja.interpret.Token;

public interface SeparatedEvaluator<T extends Token> extends Evaluator<T, SeparatedFrame<T>> {

}
