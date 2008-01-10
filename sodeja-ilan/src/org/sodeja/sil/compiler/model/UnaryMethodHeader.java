package org.sodeja.sil.compiler.model;

public class UnaryMethodHeader implements MethodHeader {
	public final UnaryMessageSelector selector;

	public UnaryMethodHeader(UnaryMessageSelector selector) {
		this.selector = selector;
	}
}
