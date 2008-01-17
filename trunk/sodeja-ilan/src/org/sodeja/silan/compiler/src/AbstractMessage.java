package org.sodeja.silan.compiler.src;

import java.util.List;

import org.sodeja.collections.CollectionUtils;
import org.sodeja.silan.compiler.Compiler;
import org.sodeja.silan.instruction.Instruction;
import org.sodeja.silan.instruction.MessageInstruction;

public abstract class AbstractMessage implements Message {
	protected void compileUnaryChain(Compiler compiler, ExecutableCode codeModel, 
			List<Instruction> instructions, List<UnaryMessage> messages) {
		if(CollectionUtils.isEmpty(messages)) {
			return;
		}
		
		for(UnaryMessage msg : messages) {
			msg.compile(compiler, codeModel, instructions);
		}
	}

	protected void compileBinaryChain(Compiler compiler, ExecutableCode codeModel, 
			List<Instruction> instructions, List<BinaryMessage> messages) {
		if(CollectionUtils.isEmpty(messages)) {
			return;
		}
		
		for(BinaryMessage binary : messages) {
			binary.compile(compiler, codeModel, instructions);
		}
	}

	protected void compileKeyword(Compiler compiler, ExecutableCode codeModel, 
			List<Instruction> instructions, KeywordMessage message) {
		if(message == null) {
			return;
		}
		
		for(KeywordMessageArgument arg : message.arguments) {
			arg.primary.compile(compiler, codeModel, instructions);
			compileUnaryChain(compiler, codeModel, instructions, arg.unaries);
			compileBinaryChain(compiler, codeModel, instructions, arg.binaries);
		}
		instructions.add(new MessageInstruction(message.selector, message.arguments.size()));
	}
}
