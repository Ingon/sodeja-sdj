package org.sodeja.rm;

class TestInstruction extends AssignInstruction {
	public TestInstruction(Machine machine, InstructionPart delegate) {
		super(machine, "flag", delegate);
	}
}
