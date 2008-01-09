package org.sodeja.sil.model;

import java.util.List;

public class BinaryMessageChain {
	public final List<BinaryMessage> messages;

	public BinaryMessageChain(List<BinaryMessage> messages) {
		this.messages = messages;
	}
}
