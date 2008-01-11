package org.sodeja.sil.runtime.protocol;

import org.sodeja.sil.runtime.SILObject;
import org.sodeja.sil.runtime.memory.InternalReference;

public class SILClassProtocol implements SILProtocol {
	SILClassProtocol() {
	}
	
	public InternalReference getInstanceSpec(SILObject obj) {
		throw new UnsupportedOperationException();
	}
}
