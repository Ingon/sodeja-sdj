package org.sodeja.rm;

public class LabelInstruction extends AbstractInstruction {
	
	private final String label;
	
	public LabelInstruction(Machine machine, String label) {
		super(machine);
		
		this.label = label;
	}

	@Override
	public void execute() {
		advancePc();
	}
}
