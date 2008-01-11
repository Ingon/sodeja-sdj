package org.sodeja.sil.runtime.protocol;

import org.sodeja.sil.runtime.SILDefaultObject;
import org.sodeja.sil.runtime.memory.InternalReference;

public class ClassProtocol implements Protocol {
	ClassProtocol() {
	}
	
	public void init(InternalReference classRef) {
		SILDefaultObject classObject = (SILDefaultObject) classRef.getValue();
		classObject.resize(classObject.size() + 5);
	}
	
	public InternalReference getSuperclass(InternalReference classRef) {
		return get(classRef, 0);
	}

	public void setSuperclass(InternalReference classRef, InternalReference supertype) {
		set(classRef, supertype, 0);
	}

	public InternalReference getMethods(InternalReference classRef) {
		return get(classRef, 1);
	}

	public void setMethods(InternalReference classRef, InternalReference methodsRef) {
		set(classRef, methodsRef, 1);
	}
	
	public InternalReference getInstanceSpec(InternalReference classRef) {
		return get(classRef, 2);
	}
	
	public void setInstanceSpec(InternalReference classRef, InternalReference instanceSpecRef) {
		set(classRef, instanceSpecRef, 2);
	}
	
	public InternalReference getSubclasses(InternalReference classRef) {
		return get(classRef, 3);
	}
	
	public void setSubclasses(InternalReference classRef, InternalReference subclassesRef) {
		set(classRef, subclassesRef, 3);
	}

	public InternalReference getInstances(InternalReference classRef) {
		return get(classRef, 4);
	}
	
	public void setInstances(InternalReference classRef, InternalReference instancesRef) {
		set(classRef, instancesRef, 4);
	}
	
//	public InternalReference getSubtype(InternalReference classRef) {
//		return get(classRef, 5);
//	}
//	
//	public void setSubtype(InternalReference classRef, InternalReference subtypeRef) {
//		set(classRef, subtypeRef, 5);
//	}
	
	private InternalReference get(InternalReference classRef, int index) {
		SILDefaultObject classObject = (SILDefaultObject) classRef.getValue();
		return classObject.at(index);
	}
	
	private void set(InternalReference classRef, InternalReference value, int index) {
		SILDefaultObject classObject = (SILDefaultObject) classRef.getValue();
		classObject.atPut(index, value);
	}
}
