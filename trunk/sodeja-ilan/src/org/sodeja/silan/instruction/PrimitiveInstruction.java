package org.sodeja.silan.instruction;

import org.sodeja.silan.ObjectManager;

public abstract class PrimitiveInstruction implements Instruction {
	
	protected final ObjectManager manager;
	private final int id;
	
	public PrimitiveInstruction(ObjectManager manager, int id) {
		this.manager = manager;
		this.id = id;
	}
}
