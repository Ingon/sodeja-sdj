package org.sodeja.sil.runtime;

public class SILObject {
	private final Reference type;
	
	public SILObject(Reference type) {
		this.type = type;
	}

	public Reference getType() {
		return type;
	}
}
