package org.sodeja.silan;

public class StoreIntegerLiteralInstruction implements Instruction {
	public final Integer value;
	
	public StoreIntegerLiteralInstruction(Integer value) {
		this.value = value;
	}

	@Override
	public void execute(Process process) {
		SILObject obj = process.vm.getObjectManager().newInteger(value);
		process.getActiveContext().push(obj);
	}
}
