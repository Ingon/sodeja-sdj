package org.sodeja.sil.runtime;

public abstract class SILObject {
	public final SILInternalReference typeClass;

	public SILObject(SILInternalReference typeClass) {
		this.typeClass = typeClass;
	}
}
