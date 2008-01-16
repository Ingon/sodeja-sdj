package org.sodeja.silan.instruction;

import org.sodeja.silan.ObjectManager;
import org.sodeja.silan.Process;
import org.sodeja.silan.SILObject;
import org.sodeja.silan.SILPrimitiveObject;
import org.sodeja.silan.context.MethodContext;

public class StringAppendInstruction extends PrimitiveInstruction {
	public StringAppendInstruction(ObjectManager manager) {
		super(manager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Process process) {
		MethodContext mc = (MethodContext) process.getActiveContext();
		
		SILPrimitiveObject<String> i1 = (SILPrimitiveObject<String>) mc.getReceiver();
		SILPrimitiveObject<String> i2 = (SILPrimitiveObject<String>) mc.pop();
		
		String primitiveResult = i1.value + i2.value;
		SILObject result = manager.newString(primitiveResult);
		mc.push(result);
	}
}
