package org.sodeja.sil.model;

public class Reference implements Operand {
	public final Identifier identifier;

	public Reference(Identifier identifier) {
		this.identifier = identifier;
	}
}
