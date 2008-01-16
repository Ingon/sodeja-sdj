package org.sodeja.silan.objects;

import org.sodeja.silan.CompiledMethod;
import org.sodeja.silan.SILClass;

public class Script {
	public final ClassDefinition classDef;
	public final MethodsDefinition classMethods;
	public final MethodsDefinition classClassMethods;
	
	public Script(ClassDefinition classDef, MethodsDefinition classMethods,
			MethodsDefinition classClassMethods) {
		this.classDef = classDef;
		this.classMethods = classMethods;
		this.classClassMethods = classClassMethods;
	}
	
	public void execute(ImageObjectManager manager) {
		// TODO add instance variable names!
//		List<String> instanceVars = classDef.instanceVars == null ? new ArrayList<String>() : classDef.instanceVars;
		manager.subclass(classDef.parentName, classDef.name, classDef.instanceVars);
		SILClass clazz = manager.getByTypeName(classDef.name);
		
		attach(manager, classMethods, clazz);
		attach(manager, classClassMethods, clazz.getType());
	}
	
	public void attach(ImageObjectManager manager, MethodsDefinition def, SILClass clazz) {
		for(Method method : def.methods) {
			CompiledMethod compiledMethod = method.compile(manager);
			clazz.addMethod(compiledMethod);
		}
	}
}
