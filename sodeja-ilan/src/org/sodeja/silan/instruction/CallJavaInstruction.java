package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;
import org.sodeja.silan.SILDefaultObject;
import org.sodeja.silan.SILPrimitiveObject;
import org.sodeja.silan.context.MethodContext;
import org.sodeja.silan.objects.ImageObjectManager;

public class CallJavaInstruction extends PrimitiveInstruction {
	public CallJavaInstruction(ImageObjectManager manager) {
		super(manager);
	}

	@Override
	public void execute(Process process) {
		SILPrimitiveObject receiver = (SILPrimitiveObject) ((MethodContext) process.getActiveContext()).getReceiver();
		SILDefaultObject message = (SILDefaultObject) process.getActiveContext().pop();
		
		String selector = ((SILPrimitiveObject<String>) message.get("selector")).value;
		throw new UnsupportedOperationException();
	}
}
