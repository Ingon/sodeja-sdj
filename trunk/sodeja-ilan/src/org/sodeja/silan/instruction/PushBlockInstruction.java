package org.sodeja.silan.instruction;

import org.sodeja.silan.CompiledBlock;
import org.sodeja.silan.Process;
import org.sodeja.silan.SILObject;

public class PushBlockInstruction implements PushInstruction {

	private CompiledBlock block;
	
	public PushBlockInstruction(CompiledBlock block) {
		this.block = block;
	}

	@Override
	public void execute(Process process) {
		SILObject obj = process.vm.objects.newBlock(block, process.getActiveContext());
		process.getActiveContext().push(obj);
	}
}
