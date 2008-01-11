package org.sodeja.sil.runtime;

import org.sodeja.sil.runtime.memory.InternalReference;

public abstract class SILObject {
	public final InternalReference typeClass;

	public SILObject(InternalReference typeClass) {
		this.typeClass = typeClass;
	}
}
