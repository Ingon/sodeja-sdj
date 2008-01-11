package org.sodeja.sil.runtime.protocol;

public class SILProtocolFactory {
	private static final SILProtocolFactory instance = new SILProtocolFactory();
	
	public static SILProtocolFactory getInstance() {
		return instance;
	}
	
	public final SILClassProtocol classProtocol = new SILClassProtocol();
	
	private SILProtocolFactory() {
	}
}
