package org.sodeja.sil.runtime;

public class InstanceSpecification {
	public final InstanceSpecificationType type;
	public final Integer fixedFieldsCount;
	public final Boolean indexFields;
	
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
}
