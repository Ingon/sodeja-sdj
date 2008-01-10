package org.sodeja.sil.runtime;

import java.util.ArrayList;

public class InstanceSpecification {
	private final InstanceSpecificationType type;
	private final Integer fixedFieldsCount;
	private final Boolean indexFields;
	
	public InstanceSpecification() {
		this.type = InstanceSpecificationType.JAVA;
		this.fixedFieldsCount = null;
		this.indexFields = null;
	}
	
	public InstanceSpecification(Integer fixedFieldsCount, Boolean indexFields) {
		this.type = InstanceSpecificationType.SIL;
		this.fixedFieldsCount = fixedFieldsCount;
		this.indexFields = indexFields;
	}

	public SILObjectContents createInstanceContents() {
		if(type == InstanceSpecificationType.SIL) {
			return new SILDefaultObjectContents(new ArrayList<Reference>(fixedFieldsCount));
		} else if(type == InstanceSpecificationType.JAVA){
			return new SILPrimitiveObjectContents();
		}
		
		throw new IllegalArgumentException("Unknown type");
	}
}
