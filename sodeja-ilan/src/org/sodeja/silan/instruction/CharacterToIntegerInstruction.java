package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;
import org.sodeja.silan.SILObject;
import org.sodeja.silan.SILPrimitiveObject;
import org.sodeja.silan.context.MethodContext;
import org.sodeja.silan.objects.ImageObjectManager;

public class CharacterToIntegerInstruction extends PrimitiveInstruction {
	public CharacterToIntegerInstruction(ImageObjectManager manager) {
		super(manager);
	}

	@Override
	public void execute(Process process) {
		MethodContext mc = (MethodContext) process.getActiveContext();
		SILPrimitiveObject<Character> i1 = (SILPrimitiveObject<Character>) mc.getReceiver();
		
		SILObject result = manager.newInteger((int) (i1.value));
		mc.push(result);
	}
}
