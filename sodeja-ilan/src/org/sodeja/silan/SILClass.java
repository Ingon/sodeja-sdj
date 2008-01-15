package org.sodeja.silan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SILClass implements SILObject {
	
	private SILClass type;
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
	
	protected void addMethod(CompiledMethod method) {
		methods.put(method.selector, method);
	}
	
	public SILClass getSuperclass() {
		return superclass;
	}

	public CompiledMethod findMethod(String selector) {
		CompiledMethod method = methods.get(selector);
		if(method != null) {
			return method;
		}
		
		if(superclass != null) {
			return superclass.findMethod(selector);
		}
		
		return null;
	}

	public int getInstanceVariablesCount() {
		return instanceVariableNames.size();
	}
	
	@Override
	public SILClass getType() {
		return type;
	}
	
	protected void setType(SILClass type) {
		if(type instanceof SILClassClass) {
			((SILClassClass) type).setInstanceClass(this);
		}
		this.type = type;
	}

	protected int getInstanceVariableIndex(String reference) {
		return instanceVariableNames.indexOf(reference);
	}
	
	@Override
	public void set(String reference, SILObject value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SILObject get(String reference) {
		throw new UnsupportedOperationException();
	}
}
