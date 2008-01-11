package org.sodeja.sil.runtime;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.sil.runtime.memory.SILInternalReference;

public class SILNamedObject extends SILObject {
	public final Map<SILInternalReference, SILInternalReference> values;
	
	public SILNamedObject(SILInternalReference clazz) {
		super(clazz);
		
		values = new HashMap<SILInternalReference, SILInternalReference>();
	}
}
