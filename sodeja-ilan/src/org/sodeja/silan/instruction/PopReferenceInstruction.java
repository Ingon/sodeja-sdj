package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;
import org.sodeja.silan.SILObject;
import org.sodeja.silan.context.Context;

public class PopReferenceInstruction implements Instruction {
	public final String reference;
	
	public PopReferenceInstruction(String reference) {
		this.reference = reference;
	}

	@Override
	public void execute(Process process) {
		Context activeContext = process.getActiveContext();
		
		SILObject obj = activeContext.pop();
		if(activeContext.update(reference, obj)) {
			return;
		}
	}
}
