package org.sodeja.rm;

class ConstInstructionPart extends AbstractInstructionPart {
	
	private Object value;
	
	public ConstInstructionPart(Machine machine, String valueStr) {
		super(machine);
		
		if(valueStr.startsWith("\"")) {
			this.value = valueStr.substring(1, valueStr.length() - 1);
			return;
		}
		
		try {
			this.value = new Integer(valueStr);
		} catch(NumberFormatException exc) {
			this.value = valueStr;
		}
	}

	@Override
	public Object execute() {
		return value;
	}
}
