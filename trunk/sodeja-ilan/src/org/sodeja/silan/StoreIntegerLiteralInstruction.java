package org.sodeja.silan;

public class StoreIntegerLiteralInstruction implements Instruction {
	public final int location;
	public final Integer value;
	
	public StoreIntegerLiteralInstruction(int location, Integer value) {
		this.location = location;
		this.value = value;
	}

	@Override
	public void execute(Process process) {
		SILObject obj = process.vm.getObjectManager().newInteger(value);
		process.getActiveContext().set(location, obj);
	}
}
