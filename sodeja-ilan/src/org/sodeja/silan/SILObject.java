package org.sodeja.silan;

public interface SILObject {
	SILClass getType();

	void set(String reference, SILObject value);

	SILObject get(String reference);
}
