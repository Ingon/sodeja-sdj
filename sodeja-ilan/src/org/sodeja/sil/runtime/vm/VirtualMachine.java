package org.sodeja.sil.runtime.vm;

import org.sodeja.sil.runtime.memory.ObjectManager;

public class VirtualMachine {
	// TODO the idea is to have thread local virtual machine eventually	
	private static final VirtualMachine instance = new VirtualMachine();
	
	public static VirtualMachine current() {
		return instance;
	}
	
	public final ObjectManager objectManager;
	
	public VirtualMachine() {
		objectManager = new ObjectManager();
	}
}
