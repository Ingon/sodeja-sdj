package org.sodeja.sil.runtime2;

import java.util.HashMap;
import java.util.Map;

public class ObjectFactory {
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
	
	private Map<String, SILPrimitiveObject<String>> symbols;
	
	public ObjectFactory() {
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
		
		SILClass systemDictionary = subclassPrimitive(dictionary);
		createPrimitiveInstance(systemDictionary, new HashMap<SILObject, SILObject>());
	}
	
	private void initInstanceSpecifications() {
		makePrimitiveInstanceSpecification();
		makeClassInstanceSpecification();
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
