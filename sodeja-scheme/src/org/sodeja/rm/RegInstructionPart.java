package org.sodeja.rm;

class RegInstructionPart extends AbstractInstructionPart {
	
	private final Register register;
	
	public RegInstructionPart(Machine machine, String registerName) {
		super(machine);
		
		this.register = machine.getRegister(registerName);
	}

	@Override
	public Object execute() {
		return register.get();
	}
}
