package org.sodeja.sil.runtime.exec;

public interface SILClass extends SILObject {
	Method findMethod(String selector);
}
