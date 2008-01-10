package org.sodeja.sil.runtime;

public class SILProcess implements SILObject {
	
	private final SILContext activeContext;
	
	public SILProcess(SILContext activeContext) {
		this.activeContext = activeContext;
	}

	@Override
	public SILClass getType() {
		throw new UnsupportedOperationException();
	}
}
