package org.sodeja.rm;

class RestoreInstruction extends AbstractInstruction {

	private final Register register;

	public RestoreInstruction(Machine machine, String registerName) {
		super(machine);
		
		this.register = machine.getRegister(registerName);
	}

	@Override
	public void execute() {
		register.set(machine.getStack().pop());
		advancePc();
	}
}
