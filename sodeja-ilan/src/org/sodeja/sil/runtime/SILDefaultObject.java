package org.sodeja.sil.runtime;

import org.sodeja.sil.runtime.memory.InternalReference;
import org.sodeja.sil.runtime.memory.ObjectManager;

public class SILDefaultObject extends SILObject {
	private InternalReference[] values;

	public SILDefaultObject(InternalReference clazz, int initialSize) {
		super(clazz);
		
		values = new InternalReference[initialSize];
		for(int i = 0;i < initialSize;i++) {
			values[i] = ObjectManager.NIL_REF;
		}
	}
	
	public InternalReference at(int index) {
		return values[index];
	}
	
	public void atPut(int index, InternalReference ref) {
		values[index] = ref;
	}
	
	public void resize(int newSize) {
		if(values.length == newSize) {
			return;
		}
		
		int mergeSize = values.length;
		if(mergeSize > newSize) {
			mergeSize = newSize;
		}
		
		InternalReference[] newValues = new InternalReference[newSize];
		System.arraycopy(values, 0, newValues, 0, mergeSize);
		for(int i = values.length, n = newSize;i < n;i++) {
			newValues[i] = ObjectManager.NIL_REF;
		}
		
		values = newValues;
	}
	
	public int size() {
		return values.length;
	}
}
