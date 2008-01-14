package org.sodeja.sil.runtime.exec;

public class CallMethodInstruction implements Instruction {
	private final int receiverIndex;
	private final String selector;
	private final int[] argumentIndexes;
	private final int resultIndex;
	
	public CallMethodInstruction(int receiverIndex, String selector, int[] argumentIndexes, int resultIndex) {
		this.receiverIndex = receiverIndex;
		this.selector = selector;
		this.argumentIndexes = argumentIndexes;
		this.resultIndex = resultIndex;
	}

	@Override
	public void perform(Process process) {
		SILObject obj = process.getActiveContext().get(receiverIndex);
		SILClass clazz = obj.getClass_();
		Method method = clazz.findMethod(selector);
		if(method != null) {
			MethodContext newActiveContext = new MethodContext(process.getActiveContext(), method, argumentIndexes, resultIndex);
			process.setActiveContext(newActiveContext);
		}
		
		method = clazz.findMethod("doesNotUnderstand:");
		MethodContext newActiveContext = new MethodContext(process.getActiveContext(), method, argumentIndexes, resultIndex);
		process.setActiveContext(newActiveContext);
		
	}
}
