package org.sodeja.silan;

import java.util.HashMap;
import java.util.Map;

public class MethodContext extends AbstractContext implements ChildContext {
	private final Context parent;
	
	private final SILObject receiver;
	private final CompiledMethod method;
	
	private final Map<String, SILObject> argumentValues;
	private final Map<String, SILObject> localValues;
	
	private int instructionPointer;
	
	public MethodContext(Context parent, SILObject receiver, 
			CompiledMethod method, SILObject[] arguments) {
		super(method.maxStackSize);
		this.parent = parent;
		
		this.receiver = receiver;
		this.method = method;
		
		if(method.arguments.size() != arguments.length) {
			throw new RuntimeException("Difference arguments count");
		}
		
		this.argumentValues = new HashMap<String, SILObject>();
		for(int i = 0, n = method.arguments.size(); i < n;i++) {
			argumentValues.put(method.arguments.get(i), arguments[i]);
		}
		
		this.localValues = new HashMap<String, SILObject>();
		
		this.instructionPointer = 0;
	}

	@Override
	public Instruction nextInstruction() {
		return method.instructions.get(instructionPointer++);
	}
	
	@Override
	public Context getParent() {
		return parent;
	}

	public SILObject getReceiver() {
		return receiver;
	}
}
