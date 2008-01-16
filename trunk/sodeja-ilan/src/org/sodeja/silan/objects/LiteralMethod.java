package org.sodeja.silan.objects;

import org.sodeja.silan.CompiledMethod;

public class LiteralMethod implements Method {

	public final String value;
	
	public LiteralMethod(String value) {
		this.value = value;
	}

	@Override
	public CompiledMethod compile(ImageObjectManager manager) {
		return manager.vm.compiler.compileMethod(value);
	}
}
