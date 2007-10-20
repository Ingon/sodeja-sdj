package org.sodeja.runtime.compiler;

import org.sodeja.runtime.Evaluator;
import org.sodeja.runtime.Expression;
import org.sodeja.runtime.Frame;

public interface CompiledExpression<C extends CompiledExpression<C>> extends Expression {
	Object eval(Evaluator<C> evaluator, Frame<C> frame);
}
