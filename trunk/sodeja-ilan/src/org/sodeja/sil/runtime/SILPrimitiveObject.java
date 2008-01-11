package org.sodeja.sil.runtime;

import org.sodeja.sil.runtime.memory.ExternalReference;
import org.sodeja.sil.runtime.memory.InternalReference;
import org.sodeja.sil.runtime.vm.VirtualMachine;

public class SILPrimitiveObject extends SILObject {
	private ExternalReference value;
	
	public SILPrimitiveObject(InternalReference clazz) {
		super(clazz);
		
		value = VirtualMachine.current().objectManager.nilExternalRef;
	}

	public ExternalReference getValue() {
		return value;
	}

	public void setValue(ExternalReference value) {
		this.value = value;
	}
}
