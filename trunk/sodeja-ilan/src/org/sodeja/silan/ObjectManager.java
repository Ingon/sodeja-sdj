package org.sodeja.silan;

import java.util.List;

import org.sodeja.collections.ListUtils;

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
				new IntegerAddInstruction(this), 
				new ReturnMethodInstruction(1));
		integer.addMethod("+", new Method(1, 1, addInstructions));
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
