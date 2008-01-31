package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;
import org.sodeja.silan.SILObject;

public class PushNewArrayInstruction implements PushInstruction {

	public final int size;
	
	public PushNewArrayInstruction(int size) {
		this.size = size;
	}

	@Override
	public void execute(Process process) {
		SILObject array = process.vm.objects.newArray(size);
		process.getActiveContext().push(array);
	}
}
