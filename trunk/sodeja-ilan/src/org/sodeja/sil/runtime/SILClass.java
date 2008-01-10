package org.sodeja.sil.runtime;

import java.util.List;
import java.util.Map;

public class SILClass implements SILObject {
	
	private SILSymbol name;
	private SILClassInstanceType instanceSpec;
	
	private SILClass superclass;
	private List<SILClass> subclasses;
	
	private Map<SILSymbol, SILCompiledMethod> methods;
	
	
	public SILClass(SILSymbol name, SILClassInstanceType instanceSpec, SILClass superclass, 
			List<SILClass> subclasses, Map<SILSymbol, SILCompiledMethod> methods) {

		this.name = name;
		this.instanceSpec = instanceSpec;
		this.superclass = superclass;
		this.subclasses = subclasses;
		this.methods = methods;
	}

	@Override
	public SILClass getType() {
		throw new UnsupportedOperationException();
	}
}
