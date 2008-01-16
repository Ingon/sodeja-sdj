package org.sodeja.silan.objects;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.CollectionUtils;
import org.sodeja.lang.StringUtils;
import org.sodeja.silan.CompiledMethod;
import org.sodeja.silan.instruction.Instruction;
import org.sodeja.silan.instruction.PrimitiveInstruction;

public class InstructionMethod implements Method {
	
	private static final String INSTRUCTIONS_PACKAGE = "org.sodeja.silan.instruction.";
	private static final String INSTRUCTION_END = "Instruction";
	
	public final MethodHeader header;
	public final MethodBlock block;
	
	public InstructionMethod(MethodHeader header, MethodBlock block) {
		this.header = header;
		this.block = block;
	}

	@Override
	public CompiledMethod compile(ImageObjectManager manager) {
		try {
			List<Instruction> instructions = loadInstructions(manager);
			return new CompiledMethod(header.selector, header.arguments, 
					header.locals, header.stackSize, instructions);
		} catch (Exception e) {
			throw new RuntimeException("Unable to load instructions for: " + header.selector);
		}
	}

	private List<Instruction> loadInstructions(ImageObjectManager manager) throws Exception {
		List<Instruction> instructions = new ArrayList<Instruction>();
		for(InstructionDefinition def : block.instructions) {
			String instructionClassName = getInstructionClass(def);
			Class<Instruction> instructionClass = (Class<Instruction>) Class.forName(instructionClassName);
			
			Instruction instruction = null;
			if(PrimitiveInstruction.class.isAssignableFrom(instructionClass)) {
				Constructor constr = instructionClass.getConstructors()[0];
				instruction = (Instruction) constr.newInstance(manager);
			} else if(! CollectionUtils.isEmpty(def.params)){
				Constructor constr = instructionClass.getConstructors()[0];
				instruction = (Instruction) constr.newInstance(getParams(def));
			} else {
				instruction = instructionClass.newInstance();
			}
			
			instructions.add(instruction);
		}
		return instructions;
	}

	private Object[] getParams(InstructionDefinition def) {
		return def.params.toArray(new Object[def.params.size()]);
	}

	private static String getInstructionClass(InstructionDefinition def) {
		return INSTRUCTIONS_PACKAGE + 
			StringUtils.capitalizeFirst(def.name) + 
			INSTRUCTION_END;
	}
}
