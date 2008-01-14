package org.sodeja.silan;

public class ReturnMethodInstruction implements Instruction {
	public ReturnMethodInstruction() {
	}

	@Override
	public void execute(Process process) {
		ChildContext mc = (ChildContext) process.getActiveContext();
		SILObject obj = mc.pop();
		
		Context parent = mc.getParent();
		parent.push(obj);
		process.setActiveContext(parent);
	}
}
