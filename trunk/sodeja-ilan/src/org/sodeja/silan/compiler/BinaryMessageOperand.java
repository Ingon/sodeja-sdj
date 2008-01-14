package org.sodeja.silan.compiler;

import java.util.List;

public class BinaryMessageOperand {
	public final Primary primary;
	public final List<UnaryMessage> unaries;
	
	public BinaryMessageOperand(Primary primary, List<UnaryMessage> unaries) {
		this.primary = primary;
		this.unaries = unaries;
	}
}
