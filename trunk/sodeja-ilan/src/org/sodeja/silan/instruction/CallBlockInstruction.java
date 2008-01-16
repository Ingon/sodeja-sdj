package org.sodeja.silan.instruction;

import org.sodeja.functional.Pair;
import org.sodeja.silan.CompiledBlock;
import org.sodeja.silan.Process;
import org.sodeja.silan.SILObject;
import org.sodeja.silan.SILPrimitiveObject;
import org.sodeja.silan.context.BlockContext;
import org.sodeja.silan.context.Context;
import org.sodeja.silan.context.MethodContext;

public class CallBlockInstruction implements Instruction {
	@SuppressWarnings("unchecked")
	@Override
	public void execute(Process process) {
		SILObject blockObj = ((MethodContext) process.getActiveContext()).getReceiver();
		SILPrimitiveObject<Pair<CompiledBlock, Context>> blockPrimObject = (SILPrimitiveObject<Pair<CompiledBlock, Context>>) blockObj;
		
		CompiledBlock block = blockPrimObject.value.first;
		Context homeContext = blockPrimObject.value.second;
		
		BlockContext blockContext = new BlockContext(process.getActiveContext(), homeContext, 
				block, extractArguments(process, block.arguments.size()));
		
		process.setActiveContext(blockContext);
	}

	private SILObject[] extractArguments(Process process, int size) {
		SILObject[] result = new SILObject[size];
		for(int i = result.length - 1;i >= 0;i--) {
			result[i] = process.getActiveContext().pop();
		}
		return result;
	}
}
