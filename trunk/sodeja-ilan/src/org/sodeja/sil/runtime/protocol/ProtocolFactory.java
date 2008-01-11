package org.sodeja.sil.runtime.protocol;

public class ProtocolFactory {
	private static final ProtocolFactory instance = new ProtocolFactory();
	
	public static ProtocolFactory getInstance() {
		return instance;
	}
	
	public final ClassProtocol classProtocol = new ClassProtocol();
	public final DictionaryProtocol dictionaryProtocol = new DictionaryProtocol();
	
	private ProtocolFactory() {
	}
}
