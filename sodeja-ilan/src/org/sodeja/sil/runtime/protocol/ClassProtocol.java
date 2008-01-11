package org.sodeja.sil.runtime.protocol;

import org.sodeja.sil.runtime.SILObject;
import org.sodeja.sil.runtime.memory.InternalReference;

public class ClassProtocol implements Protocol {
	ClassProtocol() {
	}
	
	public InternalReference getInstanceSpec(SILObject obj) {
		throw new UnsupportedOperationException();
	}
}
