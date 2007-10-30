package org.sodeja.rm;

abstract class AbstractInstructionPart implements InstructionPart {
	protected final Machine machine;

	public AbstractInstructionPart(Machine machine) {
		this.machine = machine;
	}
}
