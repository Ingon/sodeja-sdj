package org.sodeja.sil.runtime;

public class SILProtocolFactory {
	private static final SILProtocolFactory instance = new SILProtocolFactory();
	
	public static SILProtocolFactory getInstance() {
		return instance;
	}
	
	public final SILClassProtocol classProtocol = new SILClassProtocol();
	
	private SILProtocolFactory() {
	}
}
