package org.sodeja.silan;

public class PushIntegerLiteralInstruction implements Instruction {
	public final Integer value;
	
	public PushIntegerLiteralInstruction(Integer value) {
		this.value = value;
	}

	@Override
	public void execute(Process process) {
		SILObject obj = process.vm.getObjectManager().newInteger(value);
		process.getActiveContext().push(obj);
	}
}
