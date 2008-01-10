package org.sodeja.sil.compiler;

import org.sodeja.sil.runtime.method.CompiledMethod;

public class SILCompiler {
	private static final SILCompiler instance = new SILCompiler();
	
	public static SILCompiler getInstance() {
		return instance;
	}
	
	private SILCompiler() {
	}
	
	public CompiledMethod compile(String str) {
		throw new UnsupportedOperationException();
	}
}
