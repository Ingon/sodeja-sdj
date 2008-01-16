package org.sodeja.silan.objects;

import java.util.List;

import org.sodeja.collections.ListUtils;

public class InstructionDefinition {
	public final String name;
	public final List<String> params;

	public InstructionDefinition(List<String> contents) {
		this.name = ListUtils.head(contents);
		this.params = ListUtils.tail(contents);
	}
}
