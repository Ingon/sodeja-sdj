package org.sodeja.sil.runtime.protocol;

import org.sodeja.sil.runtime.SILDefaultObject;
import org.sodeja.sil.runtime.memory.InternalReference;

public class ArrayProtocol implements Protocol {
	ArrayProtocol() {
	}
	
	public void add(InternalReference arrRef, InternalReference objRef) {
		SILDefaultObject arr = (SILDefaultObject) arrRef.getValue();
		int sz = arr.size();
		arr.resize(sz + 1);
		arr.atPut(sz, objRef);
	}
}
