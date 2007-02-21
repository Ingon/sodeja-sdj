package org.sodeja.sdj.expression;

import java.util.List;

public class Program<T> {
	public final List<Supercombinator<T>> definitions;

	public Program(final List<Supercombinator<T>> definitions) {
		this.definitions = definitions;
	}
}
