package org.sodeja.silan.objects;

import java.util.List;

public class InstructionDefinition {
	public final String name;
	public final List<Object> params;

	public InstructionDefinition(String name, List<Object> contents) {
		this.name = name;
		this.params = contents;
	}
}
