package org.sodeja.sil.compiler.model;

public class UnaryMessage {
	public final UnaryMessageSelector selector;

	public UnaryMessage(UnaryMessageSelector selector) {
		this.selector = selector;
	}
}
