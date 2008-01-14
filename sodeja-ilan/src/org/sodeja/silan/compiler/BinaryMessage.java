package org.sodeja.silan.compiler;

import java.util.List;

public class BinaryMessage implements Message {
	public final String selector;
	public final Primary primary;
	public final List<UnaryMessage> unaries;
	
	public BinaryMessage(String selector, Primary primary, List<UnaryMessage> unaries) {
		this.selector = selector;
		this.primary = primary;
		this.unaries = unaries;
	}
}
