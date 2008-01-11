package org.sodeja.sil.runtime.protocol;

import org.sodeja.sil.runtime.SILDefaultObject;
import org.sodeja.sil.runtime.SILObject;
import org.sodeja.sil.runtime.memory.InternalReference;
import org.sodeja.sil.runtime.memory.ObjectManager;

public class DictionaryProtocol implements Protocol {
	DictionaryProtocol() {
	}
	
	public InternalReference get(SILObject dictionaryObj, InternalReference key) {
		SILDefaultObject dictionary = (SILDefaultObject) dictionaryObj;
		SILDefaultObject keys = (SILDefaultObject) dictionary.at(0).getValue();
		int index = findIndex(keys, key);
		if(index < 0) {
			return nil();
		}
		
		SILDefaultObject vals = (SILDefaultObject) dictionary.at(1).getValue();
		return vals.at(index);
	}
	
	public void set(SILObject dictionaryObj, InternalReference key, InternalReference value) {
		SILDefaultObject dictionary = (SILDefaultObject) dictionaryObj;
		SILDefaultObject keys = (SILDefaultObject) dictionary.at(0).getValue();
		SILDefaultObject vals = (SILDefaultObject) dictionary.at(1).getValue();
		
		int index = findIndex(keys, key);
		// Found, just update it.
		if(index > 0) {
			vals.atPut(index, value);
			return;
		}
		
		// Not found, search for empty
		int nilValueIndex = findIndex(vals, nil());
		// Found nil value, i.e. not an entry
		if(nilValueIndex > 0) {
			keys.atPut(nilValueIndex, key);
			vals.atPut(nilValueIndex, value);
			return;
		}
		
		// The dictionary is full, grow it
		int oldSize = keys.size();
		// TODO Just a guess here maybe add aditional fields in dictionary?
		int newSize = oldSize + 4;
		keys.resize(newSize);
		vals.resize(newSize);
		
		keys.atPut(oldSize, key);
		vals.atPut(oldSize, value);
	}
	
	private int findIndex(SILDefaultObject arr, InternalReference key) {
		for(int i = 0, n = arr.size();i < n;i++) {
			if(arr.at(i).equals(key)) {
				return i;
			}
		}
		
		return -1;
	}
	
	private InternalReference nil() {
		return ObjectManager.NIL_REF;
	}
}
