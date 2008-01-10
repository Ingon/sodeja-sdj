package org.sodeja.sil.runtime;

public class SILJavaObject extends SILObject {
	public final Object value;
	
	public SILJavaObject(SILClass type, Object value) {
		super(type);
		this.value = value;
	}
}
