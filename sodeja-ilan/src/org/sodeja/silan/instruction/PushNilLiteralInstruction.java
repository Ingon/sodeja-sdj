package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;

public class PushNilLiteralInstruction implements PushInstruction {
	@Override
	public void execute(Process process) {
		process.getActiveContext().push(process.vm.objects.nil());
	}
}
