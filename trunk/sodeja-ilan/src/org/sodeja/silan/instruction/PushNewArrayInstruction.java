package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;
import org.sodeja.silan.SILIndexedObject;

public class PushNewArrayInstruction implements PushInstruction {

	public final int size;
	
	public PushNewArrayInstruction(int size) {
		this.size = size;
	}

	@Override
	public void execute(Process process) {
		SILIndexedObject array = (SILIndexedObject) process.vm.objects.newArray(size);
		for(int i = size;i > 0;i--) {
			array.at_put(i, process.getActiveContext().pop());
		}
		process.getActiveContext().push(array);
	}
}
