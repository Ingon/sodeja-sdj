package org.sodeja.sil.model;

import java.util.List;

public class UnaryMessageChain {
	public final List<UnaryMessage> messages;

	public UnaryMessageChain(List<UnaryMessage> messages) {
		this.messages = messages;
	}
}
