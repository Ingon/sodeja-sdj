package org.sodeja.silan.objects;

import java.util.List;

public class ClassDefinition {
	public final String name;
	public final String parentName;
	public final List<String> instanceVars;
	
	public ClassDefinition(String name, String parentName, List<String> instanceVars) {
		this.name = name;
		this.parentName = parentName;
		this.instanceVars = instanceVars;
	}
}
