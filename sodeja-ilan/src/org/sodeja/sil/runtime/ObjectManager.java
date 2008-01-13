package org.sodeja.sil.runtime;

import java.util.HashMap;
import java.util.Map;

public class ObjectManager {
	private SILObject nilObject = new SILPrimitiveObject<Void>();
	
	private SILClass object = new SILClass();
	private SILClass objectClass = new SILClass();
	
	private SILClass metaclass = new SILClass();
	private SILClass metaclassClass = new SILClass();
	
	private SILClass clazz = new SILClass();
	private SILClass clazzClass = new SILClass();
	
	private SILClass clazzDescription = new SILClass();
	private SILClass clazzDescriptionClass = new SILClass();
	
	private SILClass instanceSpecification = new SILClass();
	private SILClass instanceSpecificationClass = new SILClass();
	
	private SILClass symbol = null;
	
	private final PrimitiveInstanceSpecification primitiveInstanceSpecification = new PrimitiveInstanceSpecification();
	private SILPrimitiveObject<PrimitiveInstanceSpecification> primitiveInstanceSpecificationObj = null;
	
	private final ClassInstanceSpecification classInstanceSpecification = new ClassInstanceSpecification();
	private SILPrimitiveObject<ClassInstanceSpecification> classInstanceSpecificationObj = null;
	
	private final CompiledMethodInstanceSpecification compiledMethodInstanceSpecification = new CompiledMethodInstanceSpecification();
	private SILPrimitiveObject<CompiledMethodInstanceSpecification> compiledMethodInstanceSpecificationObj = null;
	
	private Map<String, SILPrimitiveObject<String>> symbols = new HashMap<String, SILPrimitiveObject<String>>();

	private SILPrimitiveObject<Map<SILObject, SILObject>> systemDictionary;
	
	public ObjectManager() {
		// root hierarchy
		object.setType(objectClass);
		object.setSuperclass(nilObject);
		
		objectClass.setType(metaclass);
		objectClass.setSuperclass(clazz);
		
		metaclass.setType(metaclassClass);
		metaclass.setSuperclass(clazzDescription);
		
		metaclassClass.setType(metaclass);
		metaclassClass.setSuperclass(clazzDescriptionClass);
		
		clazz.setType(clazzClass);
		clazz.setSuperclass(clazzDescription);
		
		clazzClass.setType(metaclass);
		clazzClass.setSuperclass(clazzDescriptionClass);
		
		initFormSuper(object, clazzDescriptionClass, clazzDescription);
		
		// instance specification init
		initFormSuper(object, instanceSpecificationClass, instanceSpecification);
		initInstanceSpecifications();
		initPrimitive(instanceSpecification);
		
		// instance specifications of the roots
		object.setInstanceSpecification(makeObjectInstanceSpecification(0, 0));
		objectClass.setInstanceSpecification(classInstanceSpecificationObj);
		
		metaclass.setInstanceSpecification(classInstanceSpecificationObj);
		metaclassClass.setInstanceSpecification(classInstanceSpecificationObj);
		
		clazz.setInstanceSpecification(classInstanceSpecificationObj);
		clazzClass.setInstanceSpecification(classInstanceSpecificationObj);
		
		clazzDescription.setInstanceSpecification(classInstanceSpecificationObj);
		clazzDescriptionClass.setInstanceSpecification(classInstanceSpecificationObj);
		
		// Collection hierarchy
		SILClass collection = subclassAbstract(object);
		SILClass sequencableCollection = subclassAbstract(collection);
		SILClass arrayedCollection = subclassAbstract(sequencableCollection);
		
		SILClass array = subclassPrimitive(arrayedCollection);
		
		SILClass string = subclassPrimitive(arrayedCollection);
		symbol = subclassPrimitive(string);
		
		SILClass set = subclassAbstract(collection);
		SILClass dictionary = subclassAbstract(set);
		
		SILClass systemDictionaryClass = subclassPrimitive(dictionary);
		systemDictionary = (SILPrimitiveObject) createPrimitiveInstance(systemDictionaryClass, new HashMap<SILObject, SILObject>());
		
		// Magnitudes
		SILClass magnitude = subclassAbstract(object);
		SILClass character = subclassPrimitive(magnitude);
		
		SILClass aritmeticValue = subclassAbstract(magnitude);
		SILClass number = subclassAbstract(aritmeticValue);
		
		SILClass real = subclassPrimitive(number);
		SILClass integer = subclassPrimitive(number);
		
		// Boolean
		SILClass silBoolean = subclassAbstract(object);
		SILClass silTrue = subclassAbstract(silBoolean);
		SILClass silFalse = subclassAbstract(silBoolean);
		
		// Methods
		SILClass compiledMethod = subclass(object);
		compiledMethod.setInstanceSpecification(compiledMethodInstanceSpecificationObj);
		
		// Fill out system dictionary
		systemDictionary.getValue().put(makeSymbol("Object"), object);
		systemDictionary.getValue().put(makeSymbol("Metaclass"), metaclass);
		systemDictionary.getValue().put(makeSymbol("Class"), clazz);
		systemDictionary.getValue().put(makeSymbol("ClassDescription"), clazzDescription);
		systemDictionary.getValue().put(makeSymbol("InstanceSpecification"), instanceSpecification);
		systemDictionary.getValue().put(makeSymbol("CompiledMethod"), compiledMethod);
		
		systemDictionary.getValue().put(makeSymbol("Collection"), collection);
		systemDictionary.getValue().put(makeSymbol("SequencableCollection"), sequencableCollection);
		systemDictionary.getValue().put(makeSymbol("ArrayedCollection"), arrayedCollection);
		systemDictionary.getValue().put(makeSymbol("Array"), array);
		systemDictionary.getValue().put(makeSymbol("String"), string);
		systemDictionary.getValue().put(makeSymbol("Symbol"), symbol);
		systemDictionary.getValue().put(makeSymbol("Set"), set);
		systemDictionary.getValue().put(makeSymbol("Dictionary"), dictionary);
		systemDictionary.getValue().put(makeSymbol("SystemDictionary"), systemDictionary);
		
		systemDictionary.getValue().put(makeSymbol("Magnitude"), magnitude);
		systemDictionary.getValue().put(makeSymbol("Character"), character);
		systemDictionary.getValue().put(makeSymbol("AritmeticValue"), aritmeticValue);
		systemDictionary.getValue().put(makeSymbol("Number"), number);
		systemDictionary.getValue().put(makeSymbol("Real"), real);
		systemDictionary.getValue().put(makeSymbol("Integer"), integer);
		
		systemDictionary.getValue().put(makeSymbol("Boolean"), silBoolean);
		systemDictionary.getValue().put(makeSymbol("True"), silTrue);
		systemDictionary.getValue().put(makeSymbol("False"), silFalse);
	}
	
	private void initInstanceSpecifications() {
		makePrimitiveInstanceSpecification();
		makeClassInstanceSpecification();
		makeCompiledMethodInstanceSpecification();
	}
	
	@SuppressWarnings("unchecked")
	private void makePrimitiveInstanceSpecification() {
		primitiveInstanceSpecificationObj = (SILPrimitiveObject<PrimitiveInstanceSpecification>) primitiveInstanceSpecification.createInstance();
		primitiveInstanceSpecificationObj.setType(instanceSpecification);
		primitiveInstanceSpecificationObj.setValue(primitiveInstanceSpecification);
	}

	@SuppressWarnings("unchecked")
	private void makeClassInstanceSpecification() {
		classInstanceSpecificationObj = (SILPrimitiveObject<ClassInstanceSpecification>) primitiveInstanceSpecification.createInstance();
		classInstanceSpecificationObj.setType(instanceSpecification);
		classInstanceSpecificationObj.setValue(classInstanceSpecification);
	}

	@SuppressWarnings("unchecked")
	private void makeCompiledMethodInstanceSpecification() {
		compiledMethodInstanceSpecificationObj = (SILPrimitiveObject<CompiledMethodInstanceSpecification>) primitiveInstanceSpecification.createInstance();
		compiledMethodInstanceSpecificationObj.setType(instanceSpecification);
		compiledMethodInstanceSpecificationObj.setValue(compiledMethodInstanceSpecification);
	}
	
	@SuppressWarnings("unchecked")
	private SILObject makeObjectInstanceSpecification(int namedSize, int indexSize) {
		SILPrimitiveObject<DefaultInstanceSpecification> specObj = (SILPrimitiveObject<DefaultInstanceSpecification>) primitiveInstanceSpecification.createInstance();
		specObj.setType(instanceSpecification);
		specObj.setValue(new DefaultInstanceSpecification(namedSize, indexSize));
		return specObj;
	}
	
	private void initPrimitive(SILClass type) {
		((SILClass) type.getType()).setInstanceSpecification(classInstanceSpecificationObj);
		type.setInstanceSpecification(primitiveInstanceSpecificationObj);
	}
	
	private SILClass subclassAbstract(SILClass superclass) {
		SILClass subclass = subclass(superclass);
		subclass.setInstanceSpecification(makeObjectInstanceSpecification(0, 0));
		return subclass;
	}
	
	private SILClass subclassPrimitive(SILClass superclass) {
		SILClass subclass = subclass(superclass);
		initPrimitive(subclass);
		return subclass;
	}
	
	private SILClass subclass(SILClass superclass) {
		SILClass typeClass = new SILClass();
		SILClass type = new SILClass();
		
		initFormSuper(superclass, typeClass, type);
		typeClass.setInstanceSpecification(classInstanceSpecificationObj);
		return type;
	}
	
	private void initFormSuper(SILClass superclass, SILClass typeClass, SILClass type) {
		typeClass.setType(metaclass);
		typeClass.setSuperclass(superclass.getType());
		
		type.setType(typeClass);
		type.setSuperclass(superclass);
	}
	
	@SuppressWarnings("unchecked")
	private <T> SILPrimitiveObject<T> createPrimitiveInstance(SILClass type, T value) {
		SILPrimitiveObject<PrimitiveInstanceSpecification> instanceSpecObj = (SILPrimitiveObject<PrimitiveInstanceSpecification>) type.getInstanceSpecification();
		PrimitiveInstanceSpecification instanceSpec = ((PrimitiveInstanceSpecification) instanceSpecObj.getValue());
		
		SILPrimitiveObject<T> valueObj = (SILPrimitiveObject<T>) instanceSpec.createInstance();
		valueObj.setType(type);
		valueObj.setValue(value);
		return valueObj;
	}
	
	public SILObject makeSymbol(String str) {
		SILPrimitiveObject<String> value = symbols.get(str);
		if(value != null) {
			return value;
		}
		
		value = createPrimitiveInstance(symbol, str);
		symbols.put(str, value);
		return value;
	}
}
