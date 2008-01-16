package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;
import org.sodeja.silan.SILClass;
import org.sodeja.silan.SILDefaultObject;
import org.sodeja.silan.context.MethodContext;
import org.sodeja.silan.objects.ImageObjectManager;

public class NewObjectInstruction extends PrimitiveInstruction {
	public NewObjectInstruction(ImageObjectManager manager) {
		super(manager);
	}

	@Override
	public void execute(Process process) {
		MethodContext mc = (MethodContext) process.getActiveContext();
		SILClass type = (SILClass) mc.getReceiver();
		mc.push(new SILDefaultObject(type));
	}
}
