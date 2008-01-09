package org.sodeja.sil.model;

public class UnaryMessage {
	public final UnaryMessageSelector selector;

	public UnaryMessage(UnaryMessageSelector selector) {
		this.selector = selector;
	}
}
