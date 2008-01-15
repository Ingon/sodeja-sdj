package org.sodeja.silan;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	private final Map<String, SILClass> typesMapping;
		
	public ObjectManager(VirtualMachine virtualMachine) {
		this.vm = virtualMachine;
		
		typesMapping = new HashMap<String, SILClass>();
		
		init();
	}

	private void init() {
		List<Instruction> addInstructions = ListUtils.asList(
				new PushReferenceInstruction("aNumber"),
				new IntegerAddInstruction(this), 
				new ReturnMethodInstruction());
		integer.addMethod("+", new CompiledMethod(ListUtils.asList("aNumber"), Collections.EMPTY_LIST, 1, addInstructions));
		
		typesMapping.put("Object", object);
		typesMapping.put("Integer", integer);
		typesMapping.put("Nil", nil);
	}
	
	public SILObject getNil() {
		return nilInstance;
	}
	
	public SILObject newInteger(Integer value) {
		return new SILPrimitiveObject<Integer>(this, "Integer", value);
	}

	public SILObject newString(String value) {
		return new SILPrimitiveObject<String>(this, "String", value);
	}
	
	public void subclass(String parentName, String newClassName, List<String> instanceVariables) {
		SILClass parent = getByTypeName(parentName);
		SILClass newClass = new SILClass(parent, instanceVariables);
		typesMapping.put(newClassName, newClass);
	}
	
	protected SILClass getByTypeName(String typeName) {
		SILClass result = typesMapping.get(typeName);
		if(result == null) {
			throw new RuntimeException("Unknown type: " + typeName);
		}
		return result;
	}
}
