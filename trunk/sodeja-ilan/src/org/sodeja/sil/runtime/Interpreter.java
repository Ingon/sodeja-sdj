package org.sodeja.sil.runtime;

import java.util.List;
import java.util.Map;

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
	private List<Reference> arguments;
	private Map<String, Reference> registers;
	
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
}
