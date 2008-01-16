package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;
import org.sodeja.silan.SILPrimitiveObject;
import org.sodeja.silan.context.MethodContext;
import org.sodeja.silan.objects.ImageObjectManager;

public class IntegerToStringInstruction extends PrimitiveInstruction {
	public IntegerToStringInstruction(ImageObjectManager manager) {
		super(manager);
	}

	@Override
	public void execute(Process process) {
		MethodContext mc = (MethodContext) process.getActiveContext();
		SILPrimitiveObject<Integer> i1 = (SILPrimitiveObject<Integer>) mc.getReceiver();
		mc.push(manager.newString(String.valueOf(i1)));
	}
}
