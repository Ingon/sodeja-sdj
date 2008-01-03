package org.sodeja.il.runtime;

import java.util.HashMap;
import java.util.Map;

public class RootContext extends AbstractContext {
	
	private final Map<String, ILClass> sdk;
	
	public RootContext() {
		sdk = new HashMap<String, ILClass>();
		
		ILClass objClass = new ILClass(null);
		sdk.put("ILObject", objClass);
		sdk.put("ILSymbol", new ILClass(objClass, ILSymbol.class));
		sdk.put("ILInteger", new ILClass(objClass, ILJavaObject.class));
	}
	
	@Override
	public void define(ILSymbol name, ILObject value) {
		ILObject tvalue = super.get(name);
		if(tvalue != null) {
			throw new RuntimeException("Redefine is not possible");
		}
		super.define(name, value);
	}

	@Override
	public ILObject get(ILSymbol name) {
		ILObject temp = super.get(name);
		if(temp == null) {
			throw new RuntimeException("Not defined: " + name);
		}
		return temp;
	}

	@Override
	public RootContext getRoot() {
		return this;
	}
	
	public ILClass getClassByName(String name) {
		ILClass clazz = sdk.get(name);
		if(clazz == null) {
			throw new RuntimeException("Unknown class " + name);
		}
		return clazz;
	}
}
