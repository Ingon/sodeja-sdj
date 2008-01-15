package org.sodeja.silan.compiler.src;

import java.util.ArrayList;
import java.util.List;

public class Expression {
	public final Primary primary;
	public final List<Message> messages;
	
	public Expression(Primary primary, List<Message> cascadedMessages) {
		this.primary = primary;
		this.messages = cascadedMessages;
	}

	public Expression(Primary primary, Message message) {
		this.primary = primary;
		this.messages = new ArrayList<Message>();
		this.messages.add(message);
	}
}
