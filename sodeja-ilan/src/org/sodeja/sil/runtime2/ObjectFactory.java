package org.sodeja.sil.runtime2;

import java.util.HashMap;
import java.util.Map;

public class ObjectFactory {
	private SILObject nilObject = new SILPrimitiveObject<Void>();
	
	private SILDefaultClass object = new SILDefaultClass();
	private SILDefaultClass objectClass = new SILDefaultClass();
	
	private SILDefaultClass metaclass = new SILDefaultClass();
	private SILDefaultClass metaclassClass = new SILDefaultClass();
	
	private SILDefaultClass clazz = new SILDefaultClass();
	private SILDefaultClass clazzClass = new SILDefaultClass();
	
	private SILDefaultClass clazzDescription = new SILDefaultClass();
	private SILDefaultClass clazzDescriptionClass = new SILDefaultClass();
	
	private SILDefaultClass instanceSpecification = new SILDefaultClass();
	private SILDefaultClass instanceSpecificationClass = new SILDefaultClass();
	
	private SILDefaultClass symbol = null;
	
	private final AbstractInstanceSpecification abstractInstanceSpecification = new AbstractInstanceSpecification();
	private SILPrimitiveObject<AbstractInstanceSpecification> abstractInstanceSpecificationObj = null;
	
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
		
		// instance specifications of the roots
		object.setInstanceSpecification(makeObjectInstanceSpecification(0, 0));
		objectClass.setInstanceSpecification(classInstanceSpecificationObj);
		
		metaclass.setInstanceSpecification(classInstanceSpecificationObj);
		metaclassClass.setInstanceSpecification(classInstanceSpecificationObj);
		
		clazz.setInstanceSpecification(classInstanceSpecificationObj);
		clazzClass.setInstanceSpecification(classInstanceSpecificationObj);
		
		clazzDescription.setInstanceSpecification(classInstanceSpecificationObj);
		clazzDescriptionClass.setInstanceSpecification(classInstanceSpecificationObj);
		
		initPrimitive(instanceSpecificationClass, instanceSpecification);

		// Collection hierarchy
		SILDefaultClass collection = subclass(object);
		collection.setInstanceSpecification(makeObjectInstanceSpecification(0, 0));
		
		SILDefaultClass sequencableCollection = subclass(collection);
		sequencableCollection.setInstanceSpecification(makeObjectInstanceSpecification(0, 0));
		
		SILDefaultClass arrayedCollection = subclass(sequencableCollection);
		arrayedCollection.setInstanceSpecification(makeObjectInstanceSpecification(0, 0));
		
		SILDefaultClass array = subclass(arrayedCollection);
		array.setInstanceSpecification(primitiveInstanceSpecificationObj);
		
		SILDefaultClass string = subclass(arrayedCollection);
		string.setInstanceSpecification(primitiveInstanceSpecificationObj);
		
		symbol = subclass(string);
		
		SILDefaultClass set = subclass(collection);
		set.setInstanceSpecification(makeObjectInstanceSpecification(0, 0));
		
		SILDefaultClass dictionary = subclass(set);
		dictionary.setInstanceSpecification(makeObjectInstanceSpecification(0, 0));
		
		SILDefaultClass systemDictionary = subclass(dictionary);
		systemDictionary.setInstanceSpecification(primitiveInstanceSpecificationObj);
		createPrimitiveInstance(systemDictionary, new HashMap<SILObject, SILObject>());
		
//		initPrimitive(symbolClass, symbol);
	}
	
	private void initInstanceSpecifications() {
		makeAbstractInstanceSpecification();
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
	private void makeAbstractInstanceSpecification() {
		abstractInstanceSpecificationObj = (SILPrimitiveObject<AbstractInstanceSpecification>) primitiveInstanceSpecification.createInstance();
		abstractInstanceSpecificationObj.setType(instanceSpecification);
		abstractInstanceSpecificationObj.setValue(abstractInstanceSpecification);
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
	
	private void initPrimitive(SILDefaultClass typeClass, SILDefaultClass type) {
		typeClass.setInstanceSpecification(classInstanceSpecificationObj);
		type.setInstanceSpecification(primitiveInstanceSpecificationObj);
	}
	
	private SILDefaultClass subclass(SILDefaultClass superclass) {
		SILDefaultClass typeClass = new SILDefaultClass();
		SILDefaultClass type = new SILDefaultClass();
		
		initFormSuper(superclass, typeClass, type);
		return type;
	}
	
	private void initFormSuper(SILDefaultClass superclass, SILDefaultClass typeClass, SILDefaultClass type) {
		typeClass.setType(metaclass);
		typeClass.setSuperclass(superclass.getType());
		
		type.setType(typeClass);
		type.setSuperclass(superclass);
	}
	
	@SuppressWarnings("unchecked")
	private <T> SILPrimitiveObject<T> createPrimitiveInstance(SILDefaultClass type, T value) {
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
