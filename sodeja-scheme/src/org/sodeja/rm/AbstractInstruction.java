package org.sodeja.rm;

abstract class AbstractInstruction implements Instruction {
	protected final Machine machine;
	protected final Register pc;

	public AbstractInstruction(Machine machine) {
		this.machine = machine;
		this.pc = machine.getRegister("pc");
	}
	
	protected void advancePc() {
		Integer val = (Integer) pc.get();
		pc.set(val + 1);
	}
}
