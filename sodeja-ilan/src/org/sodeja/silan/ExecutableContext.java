package org.sodeja.silan;

import java.util.HashMap;
import java.util.Map;

public class ExecutableContext extends AbstractContext {
	
	private final CompiledCode code;
	
	private final Map<String, SILObject> localVariables;
	
	private int instructionPointer;
	
	public ExecutableContext(CompiledCode code) {
		super(code.maxStackSize);
		this.code = code;
		
		this.localVariables = new HashMap<String, SILObject>();
		
		this.instructionPointer = 0;
	}
	
	public Instruction nextInstruction() {
		if(instructionPointer >= code.instructions.size()) {
			return null;
		}
		
		return code.instructions.get(instructionPointer++);
	}
}
