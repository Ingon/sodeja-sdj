package org.sodeja.silan;

public interface Context {

	public abstract Instruction nextInstruction();

	public abstract SILObject pop();

	public abstract void push(SILObject value);

}