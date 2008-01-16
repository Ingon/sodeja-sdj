package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;
import org.sodeja.silan.SILPrimitiveObject;

public class GotoIfTrueInstruction implements Instruction {
	@Override
	public void execute(Process process) {
		SILPrimitiveObject<Boolean> bool = (SILPrimitiveObject<Boolean>) process.getActiveContext().pop();
		if(bool.value) {
			process.getActiveContext().setInstructionPointer(0);
		}
	}
}
