package org.sodeja.rm;

class SaveInstruction extends AbstractInstruction {

	private final Register register;
	
	public SaveInstruction(Machine machine, String registerName) {
		super(machine);
		
		this.register = machine.getRegister(registerName);
	}

	@Override
	public void execute() {
		machine.getStack().push(register.get());
		advancePc();
	}
}
