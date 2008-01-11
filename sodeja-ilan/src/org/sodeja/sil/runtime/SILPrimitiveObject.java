package org.sodeja.sil.runtime;

import org.sodeja.sil.runtime.memory.SILExternalReference;
import org.sodeja.sil.runtime.memory.SILInternalReference;

public class SILPrimitiveObject extends SILObject {
	private SILExternalReference value;
	
	public SILPrimitiveObject(SILInternalReference clazz) {
		super(clazz);
	}

	public SILExternalReference getValue() {
		return value;
	}

	public void setValue(SILExternalReference value) {
		this.value = value;
	}
}
