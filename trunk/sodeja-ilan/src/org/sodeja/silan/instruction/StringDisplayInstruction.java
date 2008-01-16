package org.sodeja.silan.instruction;

import org.sodeja.silan.ObjectManager;
import org.sodeja.silan.Process;
import org.sodeja.silan.SILPrimitiveObject;

public class StringDisplayInstruction extends PrimitiveInstruction {
	public StringDisplayInstruction(ObjectManager manager) {
		super(manager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Process process) {
		SILPrimitiveObject<String> str = (SILPrimitiveObject<String>) process.getActiveContext().pop();
		System.out.println(str.value);
	}
}
