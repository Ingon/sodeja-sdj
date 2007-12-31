package org.sodeja.ilan.ildk;

import java.util.HashMap;
import java.util.Map;

public class ILClassFactory {
	private static final ILClassFactory INSTANCE = new ILClassFactory();
	
	public static ILClassFactory getInstance() {
		return INSTANCE;
	}
	
	private final Map<ILSymbol, ILClass> classes = new HashMap<ILSymbol, ILClass>();
	
	protected ILClass getMakeILClass(ILSymbol obj) {
		ILClass clazz = classes.get(obj);
		if(clazz == null) {
			clazz = new ILClass(obj, ILObjectClass.getInstance());
			classes.put(obj, clazz);
		}
		return clazz;
	}
	
	public boolean hasILClass(ILSymbol name) {
		return classes.containsKey(name);
	}
	
	public ILClass getILClass(ILSymbol name) {
		return classes.get(name);
	}
	
	public ILClass makeILClass(ILSymbol name, ILClass parent) {
		ILClass newClass = new ILClass(name, parent);
		classes.put(name, parent);
		return newClass;
	}
}
