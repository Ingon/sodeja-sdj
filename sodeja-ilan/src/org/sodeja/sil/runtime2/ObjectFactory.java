package org.sodeja.sil.runtime2;

import java.util.Map;

public class ObjectFactory {
	public SILObject nilObject = new SILPrimitiveObject();
	
	public SILDefaultClass object = new SILDefaultClass();
	public SILDefaultClass objectClass = new SILDefaultClass();
	
	public SILDefaultClass metaclass = new SILDefaultClass();
	public SILDefaultClass metaclassClass = new SILDefaultClass();
	
	public SILDefaultClass clazz = new SILDefaultClass();
	public SILDefaultClass clazzClass = new SILDefaultClass();
	
	public SILDefaultClass clazzDescription = new SILDefaultClass();
	public SILDefaultClass clazzDescriptionClass = new SILDefaultClass();
	
	public SILDefaultClass instanceSpecification = new SILDefaultClass();
	public SILDefaultClass instanceSpecificationClass = new SILDefaultClass();
	
	public SILDefaultClass symbol = new SILDefaultClass();
	public SILDefaultClass symbolClass = new SILDefaultClass();
	
	private final PrimitiveInstanceSpecification primitiveInstanceSpecification = new PrimitiveInstanceSpecification();
	private SILPrimitiveObject primitiveInstanceSpecificationObj = null;
	
	private final ClassInstanceSpecification classInstanceSpecification = new ClassInstanceSpecification();
	private SILPrimitiveObject classInstanceSpecificationObj = null;
	
	private Map<String, SILPrimitiveObject> symbols;
	
	public ObjectFactory() {
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
		
		init(clazzDescriptionClass, clazzDescription);
		init(instanceSpecificationClass, instanceSpecification);
		init(symbolClass, symbol);
		
		makePrimitiveInstanceSpecification();
		makeClassInstanceSpecification();
		
		object.setInstanceSpecification(makeObjectInstanceSpecification(0, 0));
		objectClass.setInstanceSpecification(classInstanceSpecificationObj);
		
		metaclass.setInstanceSpecification(classInstanceSpecificationObj);
		metaclassClass.setInstanceSpecification(classInstanceSpecificationObj);
		
		clazz.setInstanceSpecification(classInstanceSpecificationObj);
		clazzClass.setInstanceSpecification(classInstanceSpecificationObj);
		
		clazzDescription.setInstanceSpecification(classInstanceSpecificationObj);
		clazzDescriptionClass.setInstanceSpecification(classInstanceSpecificationObj);
		
		initPrimitive(instanceSpecificationClass, instanceSpecification);
		initPrimitive(symbolClass, symbol);
	}
	
	private void makePrimitiveInstanceSpecification() {
		primitiveInstanceSpecificationObj = (SILPrimitiveObject) primitiveInstanceSpecification.createInstance();
		primitiveInstanceSpecificationObj.setType(instanceSpecification);
		primitiveInstanceSpecificationObj.setValue(primitiveInstanceSpecification);
	}
	
	private void makeClassInstanceSpecification() {
		classInstanceSpecificationObj = (SILPrimitiveObject) primitiveInstanceSpecification.createInstance();
		classInstanceSpecificationObj.setType(instanceSpecification);
		classInstanceSpecificationObj.setValue(classInstanceSpecification);
	}
	
	private SILObject makeObjectInstanceSpecification(int namedSize, int indexSize) {
		SILPrimitiveObject specObj = (SILPrimitiveObject) primitiveInstanceSpecification.createInstance();
		specObj.setType(instanceSpecification);
		specObj.setValue(new DefaultInstanceSpecification(namedSize, indexSize));
		return specObj;
	}
	
	private void initPrimitive(SILDefaultClass typeClass, SILDefaultClass type) {
		typeClass.setInstanceSpecification(classInstanceSpecificationObj);
		type.setInstanceSpecification(primitiveInstanceSpecificationObj);
	}
	
	private void init(SILDefaultClass typeClass, SILDefaultClass type) {
		typeClass.setType(metaclass);
		typeClass.setSuperclass(objectClass);
		
		type.setType(typeClass);
		type.setSuperclass(object);
	}
	
	public SILObject makeSymbol(String str) {
		SILPrimitiveObject value = symbols.get(str);
		if(value != null) {
			return value;
		}
		
		value = (SILPrimitiveObject) primitiveInstanceSpecification.createInstance();
		value.setType(symbol);
		value.setValue(str);
		symbols.put(str, value);
		return value;
	}
}
