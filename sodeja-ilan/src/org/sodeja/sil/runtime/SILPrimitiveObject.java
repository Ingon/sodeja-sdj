package org.sodeja.sil.runtime;

import org.sodeja.sil.runtime.memory.ExternalReference;
import org.sodeja.sil.runtime.memory.InternalReference;

public class SILPrimitiveObject extends SILObject {
	private ExternalReference value;
	
	public SILPrimitiveObject(InternalReference clazz) {
		super(clazz);
	}

	public ExternalReference getValue() {
		return value;
	}

	public void setValue(ExternalReference value) {
		this.value = value;
	}
}
