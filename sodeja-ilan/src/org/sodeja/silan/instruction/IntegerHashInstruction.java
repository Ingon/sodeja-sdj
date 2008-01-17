package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;
import org.sodeja.silan.SILObject;
import org.sodeja.silan.SILPrimitiveObject;
import org.sodeja.silan.context.MethodContext;
import org.sodeja.silan.objects.ImageObjectManager;

public class IntegerHashInstruction extends PrimitiveInstruction {
	public IntegerHashInstruction(ImageObjectManager manager) {
		super(manager);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(Process process) {
		MethodContext mc = (MethodContext) process.getActiveContext();
		SILPrimitiveObject<Integer> i1 = (SILPrimitiveObject<Integer>) mc.getReceiver();
		
		SILObject result = manager.newInteger(i1.value.hashCode());
		mc.push(result);
	}
}
