package org.sodeja.sil.compiler.model;

public class BinaryMethodHeader implements MethodHeader {
	public final BinaryMessageSelector selector;
	public final Identifier identifier;
	
	public BinaryMethodHeader(BinaryMessageSelector selector, Identifier identifier) {
		this.selector = selector;
		this.identifier = identifier;
	}
}
