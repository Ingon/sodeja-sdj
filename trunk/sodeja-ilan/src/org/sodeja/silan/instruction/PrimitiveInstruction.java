package org.sodeja.silan.instruction;

import org.sodeja.silan.ObjectManager;

public abstract class PrimitiveInstruction implements Instruction {
	
	private static int idCounter = 0;
	
	protected final ObjectManager manager;
	private final int id;
	
	public PrimitiveInstruction(ObjectManager manager) {
		this.manager = manager;
		this.id = idCounter++;
	}

	public int getId() {
		return id;
	}
}
