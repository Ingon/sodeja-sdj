package org.sodeja.interpret.uniform.impl;

import org.sodeja.interpret.Dialect;
import org.sodeja.interpret.Token;
import org.sodeja.interpret.uniform.UniformEvaluator;
import org.sodeja.interpret.uniform.UniformFrame;
import org.sodeja.interpret.uniform.UniformLibrary;

public abstract class AbstractUniformEvaluator<T extends Token> implements UniformEvaluator<T> {
	
	private final Dialect<T, UniformFrame<T>> dialect;
	private final UniformLibrary<T> library;
	private final UniformFrame<T> rootFrame;
	
	public AbstractUniformEvaluator(Dialect<T, UniformFrame<T>> dialect, UniformLibrary<T> library) {
		this.dialect = dialect;
		this.library = library;
		this.rootFrame = new UniformFrameImpl<T>(null);
	}
	
	@Override
	public Object eval(UniformFrame<T> frame, T token) {
		return null;
	}
}
