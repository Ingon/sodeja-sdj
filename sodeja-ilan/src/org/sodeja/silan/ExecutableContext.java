package org.sodeja.silan;

public class ExecutableContext implements Context {
	
	private final CompiledCode code;
	private final SILObject[] temps;
	private int instructionPointer;
	
	public ExecutableContext(CompiledCode code) {
		this.code = code;
		this.temps = new SILObject[code.tempCount];
		this.instructionPointer = 0;
	}
	
	public Instruction nextInstruction() {
		if(instructionPointer >= code.instructions.size()) {
			return null;
		}
		
		return code.instructions.get(instructionPointer++);
	}
	
	public SILObject get(int location) {
		return temps[location];
	}
	
	public void set(int location, SILObject value) {
		temps[location] = value;
	}
}
