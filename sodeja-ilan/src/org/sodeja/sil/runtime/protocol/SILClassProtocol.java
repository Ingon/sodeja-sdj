package org.sodeja.sil.runtime.protocol;

import org.sodeja.sil.runtime.SILObject;
import org.sodeja.sil.runtime.memory.SILInternalReference;

public class SILClassProtocol implements SILProtocol {
	SILClassProtocol() {
	}
	
	public SILInternalReference getInstanceSpec(SILObject obj) {
		throw new UnsupportedOperationException();
	}
}
