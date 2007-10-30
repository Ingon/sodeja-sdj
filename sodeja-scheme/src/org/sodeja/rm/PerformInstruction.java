package org.sodeja.rm;

class PerformInstruction extends AbstractInstruction {

	private final InstructionPart delegate;
	
	public PerformInstruction(Machine machine, InstructionPart delegate) {
		super(machine);
		
		this.delegate = delegate;
	}

	@Override
	public void execute() {
		delegate.execute();
		advancePc();
	}
}
