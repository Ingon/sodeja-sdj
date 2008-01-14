package org.sodeja.silan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractContext implements Context {

	private final Map<String, SILObject> localVariables;
	
	private final SILObject[] stack;
	private int stackPointer;
	
	public AbstractContext(List<String> locals, int stackSize) {
		localVariables = new HashMap<String, SILObject>();
		for(String local : locals) {
			localVariables.put(local, value)
		}
		
		stack = new SILObject[stackSize];
		stackPointer = 0;
	}
	
	@Override
	public SILObject pop() {
		return stack[--stackPointer];
	}

	@Override
	public void push(SILObject value) {
		stack[stackPointer++] = value;
	}
}
