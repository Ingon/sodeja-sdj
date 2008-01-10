package org.sodeja.sil.runtime;

import org.sodeja.sil.model.Reference;
import org.sodeja.sil.runtime.context.Context;
import org.sodeja.sil.runtime.instructions.Instruction;
import org.sodeja.sil.runtime.method.CompiledMethod;
import org.sodeja.sil.runtime.method.DefaultCompiledMethod;

public class Interpreter {
	
	public final ObjectTable objects;
	
	private Context activeContext;
	private Context homeContext;
	
	private CompiledMethod currentMethod;
	private Integer instructionPointer;
	private Reference receiver;
	private Reference[] argumentsAndRegisters;
	
	public Interpreter(ObjectTable objects) {
		this.objects = objects;
	}

	public void interpret() {
		while(true) {
			cycle();
		}
	}

	private void cycle() {
		Instruction instr = nextInstruction();
		instr.execute(this);
	}

	private Instruction nextInstruction() {
		Instruction instr = ((DefaultCompiledMethod) currentMethod).instructions.get(instructionPointer);
		instructionPointer = instructionPointer + 1;
		return instr;
	}
	
	public void setValue(int index, Reference ref) {
		argumentsAndRegisters[index] = ref;
	}
	
	public Reference getValue(int index) {
		return argumentsAndRegisters[index];
	}
}
