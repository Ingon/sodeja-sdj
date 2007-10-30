package org.sodeja.rm;

class GotoInstruction extends AssignInstruction {
	public GotoInstruction(Machine machine, InstructionPart delegate) {
		super(machine, "pc", delegate);
	}
}
