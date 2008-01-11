package org.sodeja.sil.runtime;

import org.sodeja.sil.runtime.memory.SILInternalReference;

public class InstanceSpecification {
	public final InstanceType type;
	public final Integer indexSize;
	
	public InstanceSpecification(InstanceType type) {
		this(type, 0);
	}
	
	public InstanceSpecification(InstanceType type, Integer indexSize) {
		this.type = type;
		this.indexSize = indexSize;
	}
	
	public SILObject makeInstance(SILInternalReference typeClass) {
		if(type == InstanceType.NAMED) {
			return new SILNamedObject(typeClass);
		} else if(type == InstanceType.INDEXED) {
			return new SILIndexedObject(typeClass, indexSize);
		} else if(type == InstanceType.JAVA) {
			return new SILPrimitiveObject(typeClass);
		}
		
		throw new RuntimeException("Illegal instance type: " + type);
	}
}
