package org.sodeja.silan;

public class MethodContext implements ChildContext {
	private final Context parent;
	
	private final SILObject receiver;
	private final CompiledMethod method;
	private final int returnIndex;
	
	private final SILObject[] temps;
	
	private int instructionPointer;
	
	public MethodContext(Context parent, SILObject receiver, 
			CompiledMethod method, int argumentIndex, int resultIndex) {
		this.parent = parent;
		
		this.receiver = receiver;
		this.method = method;
		this.returnIndex = resultIndex;
		
		this.temps = new SILObject[method.argumentCount + method.tempCount];
		this.temps[0] = parent.get(argumentIndex);
		
		this.instructionPointer = 0;
	}

	@Override
	public Instruction nextInstruction() {
		return method.instructions.get(instructionPointer++);
	}
	
	@Override
	public SILObject get(int location) {
		return temps[location];
	}

	@Override
	public void set(int location, SILObject value) {
		temps[location] = value;
	}

	@Override
	public Context getParent() {
		return parent;
	}

	public SILObject getReceiver() {
		return receiver;
	}

	public int getReturnIndex() {
		return returnIndex;
	}
}
