package org.sodeja.sil.model;

public class NilReference {
	private static final NilReference instance = new NilReference();
	
	public static NilReference getInstance() {
		return instance;
	}
	
	private NilReference() {
	}
}
