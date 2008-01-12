package org.sodeja.sil.runtime2;

public interface SILClass extends SILObject {
	public SILObject getSuperclass();
	public SILObject getInstanceSpecification();
	public SILObject getMethodDictionary();
}
