package org.sodeja.rm;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Machine {
	
	private Map<String, Register> registers;
	private Map<String, Operation> operations;
	private Stack stack;
	
	private List<Instruction> instructions;
	
	public Machine(List<String> registerNames, Map<String, Operation> operations, String text) throws Exception {
		this.registers = new HashMap<String, Register>();
		this.registers.put("pc", new Register());
		this.registers.put("flag", new Register());
		for(String name : registerNames) {
			allocateRegister(name);
		}
		
		this.operations = operations;
		
		this.stack = new Stack();
		
		Assembler asm = new Assembler(this, text);
		this.instructions = asm.instructions();
	}
	
	public Machine(List<String> registerNames, Map<String, Operation> operations, Reader reader) throws Exception {
		this.registers = new HashMap<String, Register>();
		this.registers.put("pc", new Register());
		this.registers.put("flag", new Register());
		for(String name : registerNames) {
			allocateRegister(name);
		}
		
		this.operations = operations;
		
		this.stack = new Stack();
		
		Assembler asm = new Assembler(this, reader);
		this.instructions = asm.instructions();
	}

	public void start() {
		setRegisterValue("pc", 0);
		execute();
	}
	
	private void execute() {
		Integer value = null;
		while(( value = (Integer) getRegisterValue("pc")) != null) {
			if(value >= instructions.size()) {
				return;
			}
			Instruction instr = instructions.get(value);
			instr.execute();
		}
	}
	
	public Object getRegisterValue(String name) {
		return getRegister(name).get();
	}
	
	public void setRegisterValue(String name, Object value) {
		getRegister(name).set(value);
	}
	
	private void allocateRegister(String name) {
		if(registers.containsKey(name)) {
			throw new IllegalArgumentException();
		}
		registers.put(name, new Register());
	}
	
	protected Register getRegister(String name) {
		Register result = registers.get(name);
		if(result == null) {
			throw new IllegalArgumentException();
		}
		return result;
	}
	
	protected Stack getStack() {
		return stack;
	}

	protected Operation findOperation(String value) {
		Operation operation = operations.get(value);
		if(operation == null) {
			throw new IllegalArgumentException("Unknown operation: " + value);
		}
		return operation;
	}
}
