package org.sodeja.silan;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.collections.ListUtils;
import org.sodeja.silan.instruction.Instruction;
import org.sodeja.silan.instruction.IntegerAddInstruction;
import org.sodeja.silan.instruction.IntegerNegateInstruction;
import org.sodeja.silan.instruction.IntegerRaiseInstruction;
import org.sodeja.silan.instruction.NewObjectInstruction;
import org.sodeja.silan.instruction.PushReferenceInstruction;
import org.sodeja.silan.instruction.ReturnValueInstruction;

public class ObjectManager {

	public final VirtualMachine vm;
	
	private SILClass object = new SILClass(null);
	private SILClassClass objectClass = new SILClassClass(null);
	
	private SILClass integer = new SILClass(object);
	private SILClass nil = new SILClass(object);
	
	private SILObject nilInstance = new SILObject() {
		@Override
		public SILClass getType() {
			return getByTypeName("Nil");
		}

		@Override
		public void set(String reference, SILObject value) {
			throw new RuntimeException("Trying to set value of Nil!");
		}

		@Override
		public SILObject get(String reference) {
			throw new RuntimeException("Trying to set value of Nil!");
		}};
	
	private final Map<String, SILClass> typesMapping;
		
	public ObjectManager(VirtualMachine virtualMachine) {
		this.vm = virtualMachine;
		
		typesMapping = new HashMap<String, SILClass>();
		
		init();
	}

	private void init() {
		object.setType(objectClass);
		List<Instruction> newInstructions = ListUtils.asList(
				new NewObjectInstruction(this),
				new ReturnValueInstruction());
		objectClass.addMethod(new CompiledMethod("new", Collections.EMPTY_LIST,
				Collections.EMPTY_LIST, 1, newInstructions ));
		
		List<Instruction> addInstructions = ListUtils.asList(
				new PushReferenceInstruction("aNumber"),
				new IntegerAddInstruction(this), 
				new ReturnValueInstruction());
		integer.addMethod(new CompiledMethod("+", ListUtils.asList("aNumber"), 
				Collections.EMPTY_LIST, 1, addInstructions));
		
		List<Instruction> negatedInstructions = ListUtils.asList(
				new IntegerNegateInstruction(this), 
				new ReturnValueInstruction());
		integer.addMethod(new CompiledMethod("negated", Collections.EMPTY_LIST, 
				Collections.EMPTY_LIST, 1, negatedInstructions));

		List<Instruction> raisedTo = ListUtils.asList(
				new PushReferenceInstruction("aNumber"),
				new IntegerRaiseInstruction(this), 
				new ReturnValueInstruction());
		integer.addMethod(new CompiledMethod("raisedTo:", ListUtils.asList("aNumber"), 
				Collections.EMPTY_LIST, 1, raisedTo));
		
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
		
		SILClassClass newClassClass = new SILClassClass(parent.getType());
		
		SILClass newClass = new SILClass(parent, instanceVariables);
		newClass.setType(newClassClass);
		
		typesMapping.put(newClassName, newClass);
	}

	public void attach(String className, CompiledMethod method) {
		SILClass clazz = getByTypeName(className);
		clazz.addMethod(method);
	}
	
	protected SILClass getByTypeName(String typeName) {
		return typesMapping.get(typeName);
	}

	public SILObject getGlobal(String reference) {
		return getByTypeName(reference);
	}
}
