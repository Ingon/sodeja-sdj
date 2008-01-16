package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;
import org.sodeja.silan.SILPrimitiveObject;
import org.sodeja.silan.objects.ImageObjectManager;

public class StringDisplayInstruction extends PrimitiveInstruction {
	public StringDisplayInstruction(ImageObjectManager manager) {
		super(manager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Process process) {
		SILPrimitiveObject<String> str = (SILPrimitiveObject<String>) process.getActiveContext().pop();
		System.out.println(str.value);
	}
}
