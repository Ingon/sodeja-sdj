package org.sodeja.silan.compiler;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Pair;
import org.sodeja.silan.CompiledCode;
import org.sodeja.silan.Instruction;
import org.sodeja.silan.ReturnCodeInstruction;
import org.sodeja.silan.ReturnMethodInstruction;
import org.sodeja.silan.SendMessageInstruction;
import org.sodeja.silan.StoreIntegerLiteralInstruction;
import org.sodeja.silan.VirtualMachine;

import com.sun.org.apache.bcel.internal.generic.ReturnInstruction;

public class Compiler {
	private final VirtualMachine vm;
	
	public Compiler(VirtualMachine vm) {
		this.vm = vm;
	}

	public CompiledCode compile(String string) {
		ExecutableCode code = parse(string);
		int tempCount = code.localVariables.size();
		
		int statementTempCount = 0;
		List<Instruction> instructions = new ArrayList<Instruction>();
		
		for(Statement stmt : code.statements) {
			Pair<List<Instruction>, Integer> result = compile(code, stmt);
			if(statementTempCount < result.second) {
				statementTempCount = result.second;
			}
			instructions.addAll(result.first);
		}
		
		return new CompiledCode(tempCount + statementTempCount, instructions);
	}

	private Pair<List<Instruction>, Integer> compile(ExecutableCode code, Statement stmt) {
		// TODO fix this
		StoreIntegerLiteralInstruction i3 = new StoreIntegerLiteralInstruction(0, 3);
		StoreIntegerLiteralInstruction i5 = new StoreIntegerLiteralInstruction(1, 5);
		SendMessageInstruction msg = new SendMessageInstruction(0, "+", 1, 2);
		ReturnCodeInstruction ret = new ReturnCodeInstruction(2);
		
		List<Instruction> instructions = ListUtils.asList(i3, i5, msg, ret);
		return Pair.of(instructions, 3);
	}

	private ExecutableCode parse(String string) {
		// TODO fix this
		IntegerLiteral i3 = new IntegerLiteral(3);
		IntegerLiteral i5 = new IntegerLiteral(5);
		BinaryMessage msg = new BinaryMessage("+", i5, new ArrayList<UnaryMessage>());
		Statement stmt = new Statement(null, new Expression(i3, msg));
		return new ExecutableCode(stmt);
	}
}
