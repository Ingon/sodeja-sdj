package org.sodeja.silan.compiler.src;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.silan.compiler.Compiler;
import org.sodeja.silan.instruction.Instruction;

public class Expression implements Compiling {
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

	@Override
	public void compile(Compiler compiler, ExecutableCode codeModel, List<Instruction> instructions) {
		if(messages.size() > 1) {
			throw new UnsupportedOperationException("Does not supports cascade compile");
		}
		
		primary.compile(compiler, codeModel, instructions);
		for(Message message : messages) {
			message.compile(compiler, codeModel, instructions);
		}
	}
}
