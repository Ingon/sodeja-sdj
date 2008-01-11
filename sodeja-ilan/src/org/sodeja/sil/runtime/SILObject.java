package org.sodeja.sil.runtime;

import org.sodeja.sil.runtime.memory.SILInternalReference;

public abstract class SILObject {
	public final SILInternalReference typeClass;

	public SILObject(SILInternalReference typeClass) {
		this.typeClass = typeClass;
	}
}
