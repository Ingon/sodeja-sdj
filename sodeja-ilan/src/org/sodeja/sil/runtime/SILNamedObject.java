package org.sodeja.sil.runtime;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.sil.runtime.memory.InternalReference;

public class SILNamedObject extends SILObject {
	public final Map<InternalReference, InternalReference> values;
	
	public SILNamedObject(InternalReference clazz) {
		super(clazz);
		
		values = new HashMap<InternalReference, InternalReference>();
	}
}
