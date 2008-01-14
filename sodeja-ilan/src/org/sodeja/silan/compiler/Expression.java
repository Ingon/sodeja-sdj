package org.sodeja.silan.compiler;

import java.util.List;

public class Expression {
	public final Primary primary;
	public final List<Message> cascadedMessages;
	
	public Expression(Primary primary, List<Message> cascadedMessages) {
		this.primary = primary;
		this.cascadedMessages = cascadedMessages;
	}
}
