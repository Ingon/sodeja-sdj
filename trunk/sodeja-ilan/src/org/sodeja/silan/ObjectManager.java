package org.sodeja.silan;

import java.util.Collections;
import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.silan.instruction.Instruction;
import org.sodeja.silan.instruction.IntegerAddInstruction;
import org.sodeja.silan.instruction.PushReferenceInstruction;
import org.sodeja.silan.instruction.ReturnMethodInstruction;

public class ObjectManager {

	public final VirtualMachine vm;
	
	private SILClass object = new SILClass(null);
	private SILClass integer = new SILClass(object);
	private SILClass nil = new SILClass(object);
	
	private SILObject nilInstance = new SILObject() {
		@Override
		public SILClass getType() {
			return getByTypeName("Nil");
		}};
	
	public ObjectManager(VirtualMachine virtualMachine) {
		this.vm = virtualMachine;
		init();
	}

	private void init() {
		List<Instruction> addInstructions = ListUtils.asList(
				new PushReferenceInstruction("aNumber"),
				new IntegerAddInstruction(this), 
				new ReturnMethodInstruction());
		integer.addMethod("+", new CompiledMethod(ListUtils.asList("aNumber"), Collections.EMPTY_LIST, 1, addInstructions));
	}
	
	public SILObject getNil() {
		return nilInstance;
	}
	
	public SILObject newInteger(Integer value) {
		return new SILPrimitiveObject<Integer>(this, "Integer", value);
	}

	protected SILClass getByTypeName(String typeName) {
		if("Integer".equals(typeName)) {
			return integer;
		}
		
		throw new IllegalArgumentException("");
	}
}
