package org.sodeja.silan.instruction;

import org.sodeja.silan.ObjectManager;
import org.sodeja.silan.Process;
import org.sodeja.silan.SILObject;
import org.sodeja.silan.SILPrimitiveObject;
import org.sodeja.silan.context.MethodContext;

public class IntegerRaiseInstruction extends PrimitiveInstruction {
	public IntegerRaiseInstruction(ObjectManager manager) {
		super(manager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Process process) {
		MethodContext mc = (MethodContext) process.getActiveContext();
		
		SILPrimitiveObject<Integer> i1 = (SILPrimitiveObject<Integer>) mc.getReceiver();
		SILPrimitiveObject<Integer> i2 = (SILPrimitiveObject<Integer>) mc.pop();
		
		Integer primitiveResult = (int) Math.pow(i1.value, i2.value);
		SILObject result = manager.newInteger(primitiveResult);
		mc.push(result);
	}
}
