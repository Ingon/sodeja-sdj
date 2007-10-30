package org.sodeja.rm;

class BranchInstruction extends AbstractInstruction {

	private final Register pc;
	private final Register flag;
	private final InstructionPart delegate;
	
	public BranchInstruction(Machine machine, InstructionPart delegate) {
		super(machine);
		
		this.pc = machine.getRegister("pc");
		this.flag = machine.getRegister("flag");
		this.delegate = delegate;
	}

	@Override
	public void execute() {
		Boolean val = (Boolean) flag.get();
		if(val) {
			pc.set(delegate.execute());
		} else {
			advancePc();
		}
	}
}
