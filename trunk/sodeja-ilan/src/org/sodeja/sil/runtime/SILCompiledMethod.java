package org.sodeja.sil.runtime;

import java.util.List;

public class SILCompiledMethod implements SILObject {
	public final List<SILSymbol> literals;
	public final List<Integer> instructions;
	
	public SILCompiledMethod(List<SILSymbol> literals, List<Integer> instructions) {
		this.literals = literals;
		this.instructions = instructions;
	}

	@Override
	public SILClass getType() {
		throw new UnsupportedOperationException();
	}
}
