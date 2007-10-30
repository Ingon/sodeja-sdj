package org.sodeja.rm;

class AssignInstruction extends AbstractInstruction {
	
	private final Register register;
	private final InstructionPart delegate;
	
	public AssignInstruction(Machine machine, String registerName, InstructionPart delegate) {
		super(machine);
		
		this.register = machine.getRegister(registerName);
		this.delegate = delegate;
	}

	@Override
	public void execute() {
		register.set(delegate.execute());
		advancePc();
	}
}
