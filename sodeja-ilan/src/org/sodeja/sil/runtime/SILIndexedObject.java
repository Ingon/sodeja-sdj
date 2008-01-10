package org.sodeja.sil.runtime;

public class SILIndexedObject extends SILObject {
	private Reference[] values;
	
	public SILIndexedObject(Reference type, Integer initialSize) {
		super(type);
		
		values = new Reference[initialSize];
	}
	
	public SILJavaObject<Integer> size() {
		return new SILJavaObject<Integer>(References.JAVA_INTEGER_CLASS);
	}
	
	public void changeSize(SILJavaObject<Integer> newSize) {
		int newSizeVal = newSize.getValue();
		Reference[] tvalues = new Reference[newSizeVal];
		int length = newSizeVal;
		if(values.length < newSizeVal) {
			length = values.length;
		}
		
		System.arraycopy(values, 0, tvalues, 0, length);
		
		values = tvalues;
	}
	
	public Reference at(Integer index) {
		return values[index];
	}
	
	public void at_put(Integer index, Reference value) {
		values[index] = value;
	}
}
