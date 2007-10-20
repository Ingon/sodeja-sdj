package org.sodeja.runtime.impl;

import org.sodeja.runtime.Frame;
import org.sodeja.runtime.Library;
import org.sodeja.runtime.compiler.CompiledExpression;
import org.sodeja.runtime.compiler.CompiledEvaluator;

public class CompiledEvaluatorImpl<C extends CompiledExpression<C>> implements CompiledEvaluator<C> {
	protected final Library<C> library;
	protected final Frame<C> rootFrame;
	
	public CompiledEvaluatorImpl(Library<C> library, Frame<C> rootFrame) {
		this.library = library;
		this.rootFrame = rootFrame;
		
		this.library.extend(rootFrame);
	}

	@Override
	public Object eval(Frame<C> frame, C expression) {
		return expression.eval(this, frame);
	}
}
