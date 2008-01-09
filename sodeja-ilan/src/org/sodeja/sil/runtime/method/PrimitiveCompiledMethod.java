package org.sodeja.sil.runtime.method;

import org.sodeja.sil.runtime.SILObject;

public abstract class PrimitiveCompiledMethod implements CompiledMethod {
	public final Integer argumentsCount;

	public PrimitiveCompiledMethod(Integer argumentsCount) {
		this.argumentsCount = argumentsCount;
	}
	
	public abstract SILObject execute(SILObject... arguments);
}
