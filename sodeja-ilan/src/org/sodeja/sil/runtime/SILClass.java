package org.sodeja.sil.runtime;

import java.util.Map;

public class SILClass {
	public final Reference thisclass;
	private Reference superclass;
	private Map<String, Reference> messageDictionary;
	private InstanceSpecification instanceSpecification;
	
	public SILClass(Reference thisclass, Reference superclass, InstanceSpecification instanceSpecification) {
		this.thisclass = thisclass;
		this.superclass = superclass;
		this.instanceSpecification = instanceSpecification;
	}
	
	public SILObject createInstance() {
		SILObjectContents contents = instanceSpecification.createInstanceContents();
		return new SILObject(thisclass, contents);
	}

}
