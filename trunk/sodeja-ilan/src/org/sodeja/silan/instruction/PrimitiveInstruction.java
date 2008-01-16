package org.sodeja.silan.instruction;

import org.sodeja.silan.objects.ImageObjectManager;

public abstract class PrimitiveInstruction implements Instruction {
	
	private static int idCounter = 0;
	
	protected final ImageObjectManager manager;
	private final int id;
	
	public PrimitiveInstruction(ImageObjectManager manager) {
		this.manager = manager;
		this.id = idCounter++;
	}

	public int getId() {
		return id;
	}
}
