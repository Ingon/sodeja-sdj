package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;
import org.sodeja.silan.SILObject;

public class NewArrayInstruction implements PushInstruction {

	public final int size;
	
	public NewArrayInstruction(int size) {
		this.size = size;
	}

	@Override
	public void execute(Process process) {
		SILObject array = process.vm.objects.newArray(size);
		process.getActiveContext().push(array);
	}
}
