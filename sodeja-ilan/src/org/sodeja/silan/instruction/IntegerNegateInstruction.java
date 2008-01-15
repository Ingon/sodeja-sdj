package org.sodeja.silan.instruction;

import org.sodeja.silan.ObjectManager;
import org.sodeja.silan.Process;
import org.sodeja.silan.SILObject;
import org.sodeja.silan.SILPrimitiveObject;
import org.sodeja.silan.context.MethodContext;

public class IntegerNegateInstruction extends PrimitiveInstruction {
	public IntegerNegateInstruction(ObjectManager manager) {
		super(manager);
	}
	
	@Override
	public void execute(Process process) {
		MethodContext mc = (MethodContext) process.getActiveContext();
		SILPrimitiveObject<Integer> i1 = (SILPrimitiveObject<Integer>) mc.getReceiver();
		
		SILObject result = manager.newInteger(- i1.value);
		mc.push(result);
	}
}
