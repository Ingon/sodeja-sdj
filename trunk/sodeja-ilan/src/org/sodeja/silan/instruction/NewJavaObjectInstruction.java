package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;
import org.sodeja.silan.SILObject;
import org.sodeja.silan.SILPrimitiveObject;
import org.sodeja.silan.objects.ImageObjectManager;

public class NewJavaObjectInstruction extends PrimitiveInstruction {
	public NewJavaObjectInstruction(ImageObjectManager manager) {
		super(manager);
	}

	@Override
	public void execute(Process process) {
		SILPrimitiveObject<String> val = (SILPrimitiveObject<String>) process.getActiveContext().pop();
		String typeName = val.value;
		try {
			Class clazz = Class.forName(typeName);
			SILObject value = manager.wrap(clazz.newInstance());
			process.getActiveContext().push(value);
		} catch(Exception exc) {
			throw new RuntimeException("Creation of " + typeName + " failed.", exc);
		}
	}
}
