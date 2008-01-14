package org.sodeja.sil.runtime.exec;

public class PrimitiveInstruction implements Instruction {

	private final int id;
	
	public PrimitiveInstruction(int id) {
		this.id = id;
	}

	@Override
	public void perform(Process process) {
		VirtualMachine.getInstance().primitives.invoke(process, id);
	}
}
