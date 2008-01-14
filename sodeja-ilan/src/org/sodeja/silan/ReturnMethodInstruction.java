package org.sodeja.silan;

public class ReturnMethodInstruction implements Instruction {
	
	private final int returnIndex;
	
	public ReturnMethodInstruction(int returnIndex) {
		this.returnIndex = returnIndex;
	}

	@Override
	public void execute(Process process) {
		ChildContext mc = (ChildContext) process.getActiveContext();
		SILObject obj = mc.get(returnIndex);
		
		Context parent = mc.getParent();
		parent.set(mc.getReturnIndex(), obj);
		process.setActiveContext(parent);
	}
}
