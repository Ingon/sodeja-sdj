package org.sodeja.silan;

public interface Context {

	public abstract Instruction nextInstruction();

	public abstract SILObject get(int location);

	public abstract void set(int location, SILObject value);

}