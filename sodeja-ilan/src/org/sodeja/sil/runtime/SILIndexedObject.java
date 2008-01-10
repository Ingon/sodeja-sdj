package org.sodeja.sil.runtime;

public class SILIndexedObject extends SILObject {
	private SILObject[] values;
	
	public SILIndexedObject(SILClass type, Integer initialSize) {
		super(type);
		
		values = new SILObject[initialSize];
	}
	
	public Integer size() {
		return values.length;
	}
	
	public void changeSize(Integer newSize) {
		SILObject[] tvalues = new SILObject[newSize];
		int length = newSize;
		if(values.length < newSize) {
			length = values.length;
		}
		
		System.arraycopy(values, 0, tvalues, 0, length);
		
		values = tvalues;
	}
	
	public SILObject at(Integer index) {
		return values[index];
	}
	
	public void at_put(Integer index, SILObject value) {
		values[index] = value;
	}
}
