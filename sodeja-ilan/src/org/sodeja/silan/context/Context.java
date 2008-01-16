package org.sodeja.silan.context;

import org.sodeja.silan.Process;
import org.sodeja.silan.SILObject;
import org.sodeja.silan.instruction.Instruction;

public interface Context {
	public Process getProcess();
	
	public Instruction nextInstruction();

	public void setInstructionPointer(int newValue);
	
	public SILObject pop();

	public void push(SILObject value);
	
	public SILObject resolve(String reference);

	public boolean update(String reference, SILObject pop);

	public void clear();
}