package org.sodeja.sil.runtime.memory;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.sil.runtime.InstanceSpecification;
import org.sodeja.sil.runtime.InstanceType;
import org.sodeja.sil.runtime.SILDefaultObject;
import org.sodeja.sil.runtime.SILObject;
import org.sodeja.sil.runtime.SILPrimitiveObject;
import org.sodeja.sil.runtime.protocol.ClassProtocol;
import org.sodeja.sil.runtime.protocol.DictionaryProtocol;
import org.sodeja.sil.runtime.vm.VirtualMachine;

public class ObjectManager {
	
	private final VirtualMachine vm;

	public static final InternalReference NIL_REF = new InternalReference(null, 0);
	public static final ExternalReference NIL_EXTERNAL_REF = new ExternalReference(null, 0);
	
	private int internalRefId = 1;
	private int externalRefId = 1;
	
	private InternalReference metaclassRef;
	private InternalReference metaclassClassRef;
	
	private InternalReference systemDictionaryRef;
	private InternalReference symbolClassRef;
	
	private final Map<InternalReference, SILObject> objects;
	private final Map<ExternalReference, Object> externals;
	
	private final Map<String, InternalReference> symbols;
	
	public ObjectManager(VirtualMachine vm) {
		this.vm = vm;
		
		objects = new HashMap<InternalReference, SILObject>();
		externals = new HashMap<ExternalReference, Object>();
		
		symbols = new HashMap<String, InternalReference>();
	}

	public void init() {
		metaclassRef = new InternalReference(this, internalRefId++);
		metaclassClassRef = new InternalReference(this, internalRefId++);
		
		ClassProtocol classProtocol = vm.protocols.classProtocol;
		
		// Basic object hierarchy
		put(metaclassRef, new SILDefaultObject(metaclassClassRef, 0));
		classProtocol.init(metaclassRef);
		
		put(metaclassClassRef, new SILDefaultObject(metaclassRef, 0));
		classProtocol.init(metaclassClassRef);
		
		InternalReference objectClassRef = put(nextInternalReference(), new SILDefaultObject(metaclassRef, 0));
		classProtocol.init(objectClassRef);
		
		InternalReference objectRef = put(nextInternalReference(), new SILDefaultObject(objectClassRef, 0));
		classProtocol.init(objectRef);
		
		InternalReference classClassRef = put(nextInternalReference(), new SILDefaultObject(metaclassRef, 0));
		classProtocol.init(classClassRef);
		
		InternalReference classRef = put(nextInternalReference(), new SILDefaultObject(classClassRef, 0));
		classProtocol.init(classRef);
		
		classProtocol.setSuperclass(metaclassClassRef, classClassRef);
		classProtocol.setSuperclass(metaclassRef, classRef);
		
		classProtocol.setSuperclass(objectClassRef, classRef);
		classProtocol.setSuperclass(objectRef, NIL_REF);
		
		classProtocol.setSuperclass(classClassRef, objectClassRef);
		classProtocol.setSuperclass(classRef, objectRef);
		
		// Finish init metaclass
		classProtocol.setInstanceSpec(metaclassRef, makeInstanceSpecification(4));
		
		// Symbol hierarchy
		symbolClassRef = makePrimitiveClass(objectRef, 0);
		
		// Arrays
		InternalReference arrayClassRef = makeClass(objectRef, 0, 0);
		
		// System dictionary hierarchy
		InternalReference dictionaryClassRef = makeClass(objectRef, 0, 2);
		
		// Make the system dictionary
		systemDictionaryRef = create(dictionaryClassRef);
		SILDefaultObject systemDictionary = (SILDefaultObject) systemDictionaryRef.getValue();
		systemDictionary.atPut(0, create(arrayClassRef));
		systemDictionary.atPut(1, create(arrayClassRef));
		
		DictionaryProtocol dictionaryProtocol = vm.protocols.dictionaryProtocol;

		dictionaryProtocol.set(systemDictionary, makeSymbol("Object"), objectRef);
		dictionaryProtocol.set(systemDictionary, makeSymbol("Metaclass"), metaclassRef);
		dictionaryProtocol.set(systemDictionary, makeSymbol("Class"), classRef);
		dictionaryProtocol.set(systemDictionary, makeSymbol("Symbol"), symbolClassRef);
		dictionaryProtocol.set(systemDictionary, makeSymbol("Array"), arrayClassRef);
		dictionaryProtocol.set(systemDictionary, makeSymbol("Dictionary"), dictionaryClassRef);
	}
	
	private InternalReference makeClass(InternalReference superclass, int classVariables, int instanceVariables) {
		ClassProtocol protocol = vm.protocols.classProtocol;
		
		InternalReference classClassRef = create(metaclassRef);
		protocol.init(classClassRef);
		protocol.setSuperclass(classClassRef, superclass.getValue().typeClass);
		protocol.setInstanceSpec(classClassRef, makeInstanceSpecification(classVariables));
		
		InternalReference classRef = create(classClassRef);
		protocol.init(classRef);
		protocol.setSuperclass(classRef, superclass);
		protocol.setInstanceSpec(classRef, makeInstanceSpecification(instanceVariables));
		
		return classRef;
	}

	private InternalReference makePrimitiveClass(InternalReference superclass, int classVariables) {
		ClassProtocol protocol = vm.protocols.classProtocol;
		
		InternalReference classClassRef = create(metaclassRef);
		protocol.init(classClassRef);
		protocol.setSuperclass(classClassRef, superclass.getValue().typeClass);
		protocol.setInstanceSpec(classClassRef, makeInstanceSpecification(classVariables));
		
		InternalReference classRef = create(classClassRef);
		protocol.init(classRef);
		protocol.setSuperclass(classRef, superclass);
		protocol.setInstanceSpec(classRef, makePrimitiveInstanceSpecification());
		
		return classRef;
	}
	
	private InternalReference makeInstanceSpecification(int valsCount) {
		SILPrimitiveObject prim = new SILPrimitiveObject(null);
		prim.setValue(createLink(new InstanceSpecification(InstanceType.SIL, valsCount)));
		return put(nextInternalReference(), prim);
	}

	private InternalReference makePrimitiveInstanceSpecification() {
		SILPrimitiveObject prim = new SILPrimitiveObject(null);
		prim.setValue(createLink(new InstanceSpecification(InstanceType.JAVA)));
		return put(nextInternalReference(), prim);
	}
	
	public InternalReference create(InternalReference typeClass) {
		return put(nextInternalReference(), createObject(typeClass));
	}
	
	private SILObject createObject(InternalReference typeClass) {
		InstanceSpecification instanceSpec = getInstanceSpec(typeClass);
		return instanceSpec.makeInstance(typeClass);
	}
	
	private InstanceSpecification getInstanceSpec(InternalReference typeClass) {
		InternalReference instanceRef = vm.protocols.classProtocol.getInstanceSpec(typeClass);
		SILPrimitiveObject instanceObj = (SILPrimitiveObject) instanceRef.getValue();

		return (InstanceSpecification) instanceObj.getValue().getValue();
	}
	
	private InternalReference put(InternalReference ref, SILObject obj) {
		objects.put(ref, obj);
		return ref;
	}
	
	private InternalReference nextInternalReference() {
		return new InternalReference(this, internalRefId++);
	}
	
	public SILObject get(InternalReference ref) {
		return validate(objects.get(ref));
	}
	
	public ExternalReference createLink(Object obj) {
		ExternalReference ref = new ExternalReference(this, externalRefId++);
		externals.put(ref, obj);
		return ref;
	}
	
	public Object getLink(ExternalReference ref) {
		return validate(externals.get(ref));
	}
	
	public InternalReference makeSymbol(String str) {
		InternalReference ref = symbols.get(str);
		if(ref != null) {
			return ref;
		}
		
		ref = create(symbolClassRef);
		symbols.put(str, ref);
		
		SILPrimitiveObject symbolObj = (SILPrimitiveObject) get(ref);
		symbolObj.setValue(createLink(str));
		
		return ref;
	}

	public InternalReference getSystemDictionaryRef() {
		return systemDictionaryRef;
	}
	
	private static <T> T validate(T obj) {
		if(obj == null) {
			throw new RuntimeException("Object not found");
		}
		return obj;
	}
}
