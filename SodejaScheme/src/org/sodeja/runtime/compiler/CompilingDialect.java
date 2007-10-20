package org.sodeja.runtime.compiler;

import java.util.List;

import org.sodeja.runtime.Expression;

public interface CompilingDialect<E extends Expression, C extends CompiledExpression<C>> {
	C compile(E expression);
	List<C> compileList(List<E> expressions);
}
