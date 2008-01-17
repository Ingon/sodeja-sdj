package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;
import org.sodeja.silan.SILObject;
import org.sodeja.silan.context.MethodContext;
import org.sodeja.silan.objects.ImageObjectManager;

public class ObjectsSameInstruction extends PrimitiveInstruction {
	public ObjectsSameInstruction(ImageObjectManager manager) {
		super(manager);
	}

	@Override
	public void execute(Process process) {
		MethodContext mc = (MethodContext) process.getActiveContext();
		
		SILObject obj1 = mc.getReceiver();
		SILObject obj2 = mc.pop();
		
		mc.push(manager.newBoolean(obj1 == obj2));
	}
}
