package org.sodeja.silan;

public class SendMessageInstruction implements Instruction {
	private final int receiverIndex;
	private final String selector;
	private final int argumentIndex;
	private final int resultIndex;
	
	public SendMessageInstruction(int receiverIndex, String string, 
			int argumentIndex, int resultIndex) {
		this.receiverIndex = receiverIndex;
		this.selector = string;
		this.argumentIndex = argumentIndex;
		this.resultIndex = resultIndex;
	}

	@Override
	public void execute(Process process) {
		SILObject value = process.getActiveContext().get(receiverIndex);
		SILClass clazz = value.getType();
		
		CompiledMethod method = clazz.findMethod(selector);
		if(method == null) {
			throw new UnsupportedOperationException("Implements doesNotUnderstand: logic");
		}
		
		MethodContext newContext = new MethodContext(process.getActiveContext(), value, 
				method, argumentIndex, resultIndex);
		process.setActiveContext(newContext);
	}
}
