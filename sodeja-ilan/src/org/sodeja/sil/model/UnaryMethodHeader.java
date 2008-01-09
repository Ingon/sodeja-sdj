package org.sodeja.sil.model;

public class UnaryMethodHeader implements MethodHeader {
	public final UnaryMessageSelector selector;

	public UnaryMethodHeader(UnaryMessageSelector selector) {
		this.selector = selector;
	}
}
