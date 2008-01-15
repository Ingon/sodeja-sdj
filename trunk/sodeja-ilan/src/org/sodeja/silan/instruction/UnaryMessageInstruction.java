package org.sodeja.silan.instruction;

import org.sodeja.silan.CompiledMethod;
import org.sodeja.silan.Process;
import org.sodeja.silan.SILClass;
import org.sodeja.silan.SILObject;
import org.sodeja.silan.context.MethodContext;

public class UnaryMessageInstruction implements Instruction {
	private final String selector;
	
	public UnaryMessageInstruction(String string) {
		this.selector = string;
	}

	@Override
	public void execute(Process process) {
		SILObject receiver = process.getActiveContext().pop();
		
		SILClass clazz = receiver.getType();
		
		CompiledMethod method = clazz.findMethod(selector);
		if(method == null) {
			throw new UnsupportedOperationException("Implements doesNotUnderstand: " + selector);
		}
		
		MethodContext newContext = new MethodContext(process.getActiveContext(), receiver,
				method, new SILObject[] {});
		process.setActiveContext(newContext);
	}
}
