package org.sodeja.sil.runtime.exec;

public class ReturnMethodInstruction implements Instruction {
	
	private final int returnIndex;
	
	public ReturnMethodInstruction(int returnIndex) {
		this.returnIndex = returnIndex;
	}

	@Override
	public void perform(Process process) {
		MethodContext activeContext = (MethodContext) process.getActiveContext();
		Context parentContext = activeContext.parentContext;
		
		SILObject temp = activeContext.get(returnIndex);
		parentContext.set(activeContext.resultIndex, temp);
		process.setActiveContext(parentContext);
	}
}
