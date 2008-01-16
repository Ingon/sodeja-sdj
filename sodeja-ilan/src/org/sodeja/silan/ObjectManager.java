package org.sodeja.silan;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Pair;
import org.sodeja.silan.context.Context;
import org.sodeja.silan.instruction.CallBlockInstruction;
import org.sodeja.silan.instruction.Instruction;
import org.sodeja.silan.instruction.IntegerAddInstruction;
import org.sodeja.silan.instruction.IntegerLessThenInstruction;
import org.sodeja.silan.instruction.IntegerMultyInstruction;
import org.sodeja.silan.instruction.IntegerNegateInstruction;
import org.sodeja.silan.instruction.IntegerRaiseInstruction;
import org.sodeja.silan.instruction.NewObjectInstruction;
import org.sodeja.silan.instruction.PushReferenceInstruction;
import org.sodeja.silan.instruction.ReturnSelfInstruction;
import org.sodeja.silan.instruction.ReturnValueInstruction;
import org.sodeja.silan.instruction.StringAppendInstruction;
import org.sodeja.silan.instruction.StringDisplayInstruction;

public class ObjectManager implements TypeSupplier {

	public final VirtualMachine vm;
	
	private SILClass object = new SILClass(null);
	private SILClassClass objectClass = new SILClassClass(null);
	
	private SILClass integer = new SILClass(object);
	private SILClass string = new SILClass(object);
	
	private SILClass nil = new SILClass(object);
	private SILObject nilInstance = new SILPrimitiveObject<Void>(this, "Nil", null);
	
	private SILClass bool = new SILClass(object);
	
	private SILClass trueClass = new SILClass(bool);
	private SILClass falseClass = new SILClass(bool);
	
	private SILObject trueInstance = new SILPrimitiveObject<Boolean>(this, "True", Boolean.TRUE);
	private SILObject falseInstance = new SILPrimitiveObject<Boolean>(this, "False", Boolean.FALSE);
	
	private SILClass compiledBlock = new SILClass(object);
	
	private final Map<String, SILClass> typesMapping;
		
	public ObjectManager(VirtualMachine virtualMachine) {
		this.vm = virtualMachine;
		
		typesMapping = new HashMap<String, SILClass>();
		
//		init();
	}

//	private void init() {
//		object.setType(objectClass);
//		List<Instruction> newInstructions = ListUtils.asList(
//				new NewObjectInstruction(this),
//				new ReturnValueInstruction());
//		objectClass.addMethod(new CompiledMethod("new", 
//				Collections.EMPTY_LIST, 1, newInstructions));
//		
//		List<Instruction> dnuInstructions = ListUtils.asList(
//				);
//		object.addMethod(new CompiledMethod("doesNotUnderstand:", 
//				ListUtils.asList("aMessage"), 0, dnuInstructions));
//		
//		initInteger();
//		initString();
//		
//		typesMapping.put("Object", object);
//		typesMapping.put("Integer", integer);
//		typesMapping.put("String", string);
//		typesMapping.put("Boolean", bool);
//		typesMapping.put("True", trueClass);
//		typesMapping.put("False", falseClass);
//		typesMapping.put("CompiledBlock", compiledBlock);
//		typesMapping.put("Nil", nil);
//		
//		subclass("Object", "Transcript", Collections.EMPTY_LIST);
//		SILClassClass transcript = (SILClassClass) getByTypeName("Transcript").getType();
//		
//		List<Instruction> showInstructions = ListUtils.asList(
//				new PushReferenceInstruction("aString"),
//				new StringDisplayInstruction(this), 
//				new ReturnSelfInstruction());
//		transcript.addMethod(new CompiledMethod("show:", 
//				ListUtils.asList("aString"), 1, showInstructions));
//		
//		
//		initBlock();
//	}
//
//	private void initInteger() {
//		List<Instruction> addInstructions = ListUtils.asList(
//				new PushReferenceInstruction("aNumber"),
//				new IntegerAddInstruction(this), 
//				new ReturnValueInstruction());
//		integer.addMethod(new CompiledMethod("+", 
//				ListUtils.asList("aNumber"), 1, addInstructions));
//
//		List<Instruction> multyInstructions = ListUtils.asList(
//				new PushReferenceInstruction("aNumber"),
//				new IntegerMultyInstruction(this), 
//				new ReturnValueInstruction());
//		integer.addMethod(new CompiledMethod("*", 
//				ListUtils.asList("aNumber"), 1, multyInstructions));
//		
//		List<Instruction> negatedInstructions = ListUtils.asList(
//				new IntegerNegateInstruction(this), 
//				new ReturnValueInstruction());
//		integer.addMethod(new CompiledMethod("negated", 
//				Collections.EMPTY_LIST, 1, negatedInstructions));
//
//		List<Instruction> raisedTo = ListUtils.asList(
//				new PushReferenceInstruction("aNumber"),
//				new IntegerRaiseInstruction(this), 
//				new ReturnValueInstruction());
//		integer.addMethod(new CompiledMethod("raisedTo:", 
//				ListUtils.asList("aNumber"), 1, raisedTo));
//		
//		List<Instruction> lessThan = ListUtils.asList(
//				new PushReferenceInstruction("aNumber"),
//				new IntegerLessThenInstruction(this), 
//				new ReturnValueInstruction());
//		integer.addMethod(new CompiledMethod("<", 
//				ListUtils.asList("aNumber"), 1, lessThan));
//	}
//	
//	private void initString() {
//		List<Instruction> appendInstructions = ListUtils.asList(
//				new PushReferenceInstruction("aString"),
//				new StringAppendInstruction(this), 
//				new ReturnValueInstruction());
//		string.addMethod(new CompiledMethod(",", 
//				ListUtils.asList("aString"), 1, appendInstructions ));
//	}
//	
//	private void initBlock() {
//		List<Instruction> blockInstructions = ListUtils.asList(
//				new CallBlockInstruction(),
//				new ReturnValueInstruction());
//		compiledBlock.addMethod(new CompiledMethod("value", 
//				Collections.EMPTY_LIST, 0, blockInstructions));
//
//		List<Instruction> blockInstructions1 = ListUtils.asList(
//				new PushReferenceInstruction("anObject"),
//				new CallBlockInstruction(),
//				new ReturnValueInstruction());
//		compiledBlock.addMethod(new CompiledMethod("value:", 
//				ListUtils.asList("anObject"), 1, blockInstructions1));
//
//		List<Instruction> blockInstructions2 = ListUtils.asList(
//				new PushReferenceInstruction("anObject1"),
//				new PushReferenceInstruction("anObject2"),
//				new CallBlockInstruction(),
//				new ReturnValueInstruction());
//		compiledBlock.addMethod(new CompiledMethod("value:value:", 
//				ListUtils.asList("anObject1", "anObject2"), 2, blockInstructions2));
//	}
	
	public SILObject getNil() {
		return nilInstance;
	}
	
	public SILObject newInteger(Integer value) {
		return new SILPrimitiveObject<Integer>(this, "Integer", value);
	}

	public SILObject newString(String value) {
		return new SILPrimitiveObject<String>(this, "String", value);
	}
	
	public SILObject newBoolean(Boolean value) {
		if(value) {
			return trueInstance;
		}
		return falseInstance;
	}
	
	public SILObject newBlock(CompiledBlock block, Context ctx) {
		return new SILPrimitiveObject<Pair<CompiledBlock, Context>>(this, "CompiledBlock", Pair.of(block, ctx));
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
	
	public SILClass getByTypeName(String typeName) {
		return typesMapping.get(typeName);
	}

	public SILObject getGlobal(String reference) {
		return getByTypeName(reference);
	}
}
