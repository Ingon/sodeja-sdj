package org.sodeja.silan;

public abstract class PrimitiveInstruction implements Instruction {
	
	protected final ObjectManager manager;
	private final int id;
	
	public PrimitiveInstruction(ObjectManager manager, int id) {
		this.manager = manager;
		this.id = id;
	}
}
