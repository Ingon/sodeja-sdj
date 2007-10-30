package org.sodeja.rm;

public class LabelInstructionPart extends AbstractInstructionPart {
	
	private final String label;
	private final Integer value;
	
	public LabelInstructionPart(Machine machine, String label, Integer value) {
		super(machine);
		
		this.label = label;
		this.value = value;
	}

	@Override
	public Object execute() {
		return value;
	}
}
