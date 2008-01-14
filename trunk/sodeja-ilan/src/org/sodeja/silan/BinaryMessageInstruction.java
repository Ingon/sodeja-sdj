package org.sodeja.silan;

public class BinaryMessageInstruction implements Instruction {
	private final String selector;
	
	public BinaryMessageInstruction(String string) {
		this.selector = string;
	}

	@Override
	public void execute(Process process) {
		SILObject argument = process.getActiveContext().pop();
		SILObject receiver = process.getActiveContext().pop();
		
		SILClass clazz = receiver.getType();
		
		CompiledMethod method = clazz.findMethod(selector);
		if(method == null) {
			throw new UnsupportedOperationException("Implements doesNotUnderstand: logic");
		}
		
		MethodContext newContext = new MethodContext(process.getActiveContext(), receiver,
				method, new SILObject[] {argument});
		process.setActiveContext(newContext);
	}
}
