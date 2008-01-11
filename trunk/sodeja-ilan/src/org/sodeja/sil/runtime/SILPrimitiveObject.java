package org.sodeja.sil.runtime;

import org.sodeja.sil.runtime.memory.ExternalReference;
import org.sodeja.sil.runtime.memory.InternalReference;
import org.sodeja.sil.runtime.memory.ObjectManager;

public class SILPrimitiveObject extends SILObject {
	private ExternalReference value;
	
	public SILPrimitiveObject(InternalReference clazz) {
		super(clazz);
		
		value = ObjectManager.NIL_EXTERNAL_REF;
	}

	public ExternalReference getValue() {
		return value;
	}

	public void setValue(ExternalReference value) {
		this.value = value;
	}
}
