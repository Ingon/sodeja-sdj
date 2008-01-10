package org.sodeja.sil.runtime;

public class SILIndexedObject extends SILObject {
	public final SILObject[] values;
	
	public SILIndexedObject(SILClass type, Integer initialSize) {
		super(type);
		
		values = new SILObject[initialSize];
	}
}
