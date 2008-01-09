package org.sodeja.sil.runtime;

public class InstanceSpecification {
	public final Integer fixedFieldsCount;
	public final Boolean indexFields;
	
	public InstanceSpecification(Integer fixedFieldsCount, Boolean indexFields) {
		this.fixedFieldsCount = fixedFieldsCount;
		this.indexFields = indexFields;
	}
}
