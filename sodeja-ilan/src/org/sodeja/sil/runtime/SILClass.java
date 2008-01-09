package org.sodeja.sil.runtime;

import java.util.Map;

public class SILClass {
	public final Reference superclass;
	public final Map<String, Reference> messageDictionary;
	public final InstanceSpecification instanceSpecification;
	
	public SILClass(Reference superclass, Map<String, Reference> messageDictionary, InstanceSpecification instanceSpecification) {
		this.superclass = superclass;
		this.messageDictionary = messageDictionary;
		this.instanceSpecification = instanceSpecification;
	}
}
