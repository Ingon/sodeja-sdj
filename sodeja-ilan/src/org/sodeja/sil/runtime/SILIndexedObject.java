package org.sodeja.sil.runtime;

import org.sodeja.sil.runtime.memory.SILInternalReference;

public class SILIndexedObject extends SILObject {
	public final SILInternalReference[] values;
	
	public SILIndexedObject(SILInternalReference clazz, int initialSize) {
		super(clazz);
		
		values = new SILInternalReference[initialSize];
	}
}
