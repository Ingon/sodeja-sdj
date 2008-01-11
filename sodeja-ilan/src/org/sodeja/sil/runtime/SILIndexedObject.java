package org.sodeja.sil.runtime;

import org.sodeja.sil.runtime.memory.InternalReference;

public class SILIndexedObject extends SILObject {
	public final InternalReference[] values;
	
	public SILIndexedObject(InternalReference clazz, int initialSize) {
		super(clazz);
		
		values = new InternalReference[initialSize];
	}
}
