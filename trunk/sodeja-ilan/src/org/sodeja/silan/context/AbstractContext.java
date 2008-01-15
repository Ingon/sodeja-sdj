package org.sodeja.silan.context;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.silan.CompiledCode;
import org.sodeja.silan.Process;
import org.sodeja.silan.SILObject;
import org.sodeja.silan.instruction.Instruction;

public abstract class AbstractContext implements Context {

	private final Process process;
	
	private final CompiledCode code;
	private final Map<String, SILObject> localVariables;
	
	private final SILObject[] stack;
	
	private int stackPointer;
	private int instructionPointer;
	
	public AbstractContext(Process process, CompiledCode code) {
		this.process = process;
		this.code = code;
		
		localVariables = new HashMap<String, SILObject>();
		for(String local : code.localVariables) {
			localVariables.put(local, process.vm.getObjectManager().getNil());
		}
		
		stack = new SILObject[code.maxStackSize];
		stackPointer = 0;
	}
	
	@Override
	public final SILObject pop() {
		SILObject value = stack[--stackPointer];
		return value;
	}

	@Override
	public final void push(SILObject value) {
		stack[stackPointer++] = value;
	}

	@Override
	public final void clear() {
		stackPointer = 0;
	}

	@Override
	public final Process getProcess() {
		return process;
	}

	@Override
	public final Instruction nextInstruction() {
		if(instructionPointer >= code.instructions.size()) {
			return null;
		}
		
		return code.instructions.get(instructionPointer++);
	}
	
	@Override
	public void update(String reference, SILObject value) {
		SILObject oldValue = localVariables.get(reference);
		if(oldValue == null) {
			throw new UnsupportedOperationException();
		}
		
		localVariables.put(reference, value);
	}

	@Override
	public SILObject resolve(String reference) {
		return localVariables.get(reference);
	}
}
