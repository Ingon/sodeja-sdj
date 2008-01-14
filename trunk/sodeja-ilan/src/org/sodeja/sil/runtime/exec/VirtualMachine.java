package org.sodeja.sil.runtime.exec;

public class VirtualMachine {
	private static final VirtualMachine instance = new VirtualMachine();
	
	public static VirtualMachine getInstance() {
		return instance;
	}
	
	public final Primitives primitives;

	public VirtualMachine() {
		primitives = new Primitives();
	}
}
