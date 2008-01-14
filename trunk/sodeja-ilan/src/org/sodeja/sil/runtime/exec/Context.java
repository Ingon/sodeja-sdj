package org.sodeja.sil.runtime.exec;

public interface Context {
	SILObject get(int index);
	void set(int index, SILObject obj);
}
