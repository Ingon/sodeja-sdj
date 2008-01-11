package org.sodeja.sil.runtime.vm;

import org.sodeja.sil.runtime.memory.ObjectManager;
import org.sodeja.sil.runtime.protocol.ProtocolFactory;

public class VirtualMachine {
	// TODO the idea is to have thread local virtual machine eventually	
	private static final VirtualMachine instance = new VirtualMachine();
	
	public static VirtualMachine current() {
		return instance;
	}
	
	public final ProtocolFactory protocols = ProtocolFactory.getInstance();
	public final ObjectManager objectManager;
	
	public VirtualMachine() {
		objectManager = new ObjectManager(this);
		objectManager.init();
	}

	public void run() {
	}
}
