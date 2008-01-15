package org.sodeja.silan;

public interface Context {
	public Process getProcess();
	
	public Instruction nextInstruction();

	public SILObject pop();

	public void push(SILObject value);
	
	public SILObject resolve(String reference);

	public void update(String reference, SILObject pop);

	public void clear();
}