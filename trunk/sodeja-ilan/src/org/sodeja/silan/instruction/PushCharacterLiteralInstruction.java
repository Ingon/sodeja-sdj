package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;
import org.sodeja.silan.SILObject;

public class PushCharacterLiteralInstruction implements PushInstruction {
	public final Character value;
	
	public PushCharacterLiteralInstruction(Character value) {
		this.value = value;
	}

	@Override
	public void execute(Process process) {
		SILObject obj = process.vm.objects.newCharacter(value);
		process.getActiveContext().push(obj);
	}
}
