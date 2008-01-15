package org.sodeja.silan.instruction;

import org.sodeja.silan.ObjectManager;
import org.sodeja.silan.Process;
import org.sodeja.silan.SILClass;
import org.sodeja.silan.SILDefaultObject;
import org.sodeja.silan.context.MethodContext;

public class NewObjectInstruction extends PrimitiveInstruction {
	public NewObjectInstruction(ObjectManager manager) {
		super(manager);
	}

	@Override
	public void execute(Process process) {
		MethodContext mc = (MethodContext) process.getActiveContext();
		SILClass type = (SILClass) mc.getReceiver();
		mc.push(new SILDefaultObject(type));
	}
}
