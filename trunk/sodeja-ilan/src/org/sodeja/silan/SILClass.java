package org.sodeja.silan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SILClass implements SILObject {
	
	private final SILClass superclass;
	private final Map<String, CompiledMethod> methods;
	
	private final List<String> instanceVariableNames;
	private final List<SILClass> subclasses;
	
	public SILClass(SILClass superclass) {
		this.superclass = superclass;
		if(superclass != null) {
			this.superclass.subclasses.add(this);
		}
		
		this.methods = new HashMap<String, CompiledMethod>();
		this.instanceVariableNames = new ArrayList<String>();
		this.subclasses = new ArrayList<SILClass>();
	}
	
	public SILClass(SILClass superclass, List<String> instanceVariableNames) {
		this(superclass);
		// TODO add the variables from super
		this.instanceVariableNames.addAll(instanceVariableNames);
	}
	
	protected void addMethod(String selector, CompiledMethod method) {
		methods.put(selector, method);
	}
	
	public SILClass getSuperclass() {
		return superclass;
	}

	public CompiledMethod findMethod(String selector) {
		return methods.get(selector);
	}

	@Override
	public SILClass getType() {
		throw new UnsupportedOperationException();
	}
}
