package org.sodeja.silan.instruction;

import org.sodeja.silan.CompiledMethod;
import org.sodeja.silan.Process;
import org.sodeja.silan.SILClass;
import org.sodeja.silan.SILObject;
import org.sodeja.silan.context.MethodContext;

public class KeywordMessageInstruction implements Instruction {

	private final String selector;
	private final int size;
	
	public KeywordMessageInstruction(String selector, int size) {
		this.selector = selector;
		this.size = size;
	}

	@Override
	public void execute(Process process) {
		SILObject[] arguments = extractArguments(process);
		SILObject receiver = process.getActiveContext().pop();

		SILClass clazz = receiver.getType();
		
		CompiledMethod method = clazz.findMethod(selector);
		if(method == null) {
			throw new UnsupportedOperationException("Implements doesNotUnderstand: " + selector);
		}
		
		MethodContext newContext = new MethodContext(
				process.getActiveContext(), receiver, method, arguments);
		process.setActiveContext(newContext);
	}

	private SILObject[] extractArguments(Process process) {
		SILObject[] result = new SILObject[size];
		for(int i = size - 1;i >= 0;i--) {
			result[i] = process.getActiveContext().pop();
		}
		return result;
	}
}
