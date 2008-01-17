package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;
import org.sodeja.silan.SILPrimitiveObject;
import org.sodeja.silan.context.MethodContext;
import org.sodeja.silan.objects.ImageObjectManager;

public class CharacterEqualInstruction extends PrimitiveInstruction {
	public CharacterEqualInstruction(ImageObjectManager manager) {
		super(manager);
	}

	@Override
	public void execute(Process process) {
		MethodContext mc = (MethodContext) process.getActiveContext();
		
		SILPrimitiveObject<Character> i1 = (SILPrimitiveObject<Character>) mc.getReceiver();
		SILPrimitiveObject<Character> i2 = (SILPrimitiveObject<Character>) mc.pop();
		
		mc.push(manager.newBoolean((char) i1.value == (char) i2.value));
	}
}
