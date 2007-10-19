package org.sodeja.runtime.compiler;

import org.sodeja.runtime.Expression;

public interface CompilingForm<E extends Expression, C extends CompiledExpression<C>> {
	C compile(CompilingDialect<E, C> dialect, E expression);
}
