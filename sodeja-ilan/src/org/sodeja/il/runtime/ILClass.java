package org.sodeja.il.runtime;

import java.util.HashMap;
import java.util.Map;

public class ILClass {
	private final ILClass parent;
	private final Class primitiveClass;
	private final Map<String, ILLambda> methods;
	
	public ILClass(ILClass parent) {
		this(parent, null);
	}
	
	public ILClass(ILClass parent, Class primitiveClass) {
		this.parent = parent;
		this.primitiveClass = primitiveClass;
		
		this.methods = new HashMap<String, ILLambda>();
	}

	public ILObject makeInstance(Object value) {
		if(primitiveClass == null) {
			throw new RuntimeException("Trying to make primitive without primitive ?");
		}
		try {
			return (ILObject) primitiveClass.getConstructors()[0].newInstance(this, value);
		} catch (Exception exc) {
			throw new RuntimeException(exc);
		}
	}
}
