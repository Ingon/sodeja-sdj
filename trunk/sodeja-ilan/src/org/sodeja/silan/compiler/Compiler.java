package org.sodeja.silan.compiler;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Pair;
import org.sodeja.silan.CompiledCode;
import org.sodeja.silan.CompiledMethod;
import org.sodeja.silan.Instruction;
import org.sodeja.silan.ReturnCodeInstruction;
import org.sodeja.silan.SendMessageInstruction;
import org.sodeja.silan.StoreIntegerLiteralInstruction;
import org.sodeja.silan.VirtualMachine;

public class Compiler {
	private final VirtualMachine vm;
	
	private final CompilerLexer lexer;
	private final CompilerParser parser;
	
	public Compiler(VirtualMachine vm) {
		this.vm = vm;
		
		this.lexer = new CompilerLexer();
		this.parser = new CompilerParser();
	}

	public CompiledCode compileCode(String codeSource) {
		List<String> tokens = lexer.lexify(codeSource);
		ExecutableCode code = parser.parseCode(tokens);
		
		throw new UnsupportedOperationException();
//		ExecutableCode code = parser.parseCode(codeSource);
//		int tempCount = code.localVariables.size();
//		
//		int statementTempCount = 0;
//		List<Instruction> instructions = new ArrayList<Instruction>();
//		
//		for(Statement stmt : code.statements) {
//			Pair<List<Instruction>, Integer> result = compile(code, stmt);
//			if(statementTempCount < result.second) {
//				statementTempCount = result.second;
//			}
//			instructions.addAll(result.first);
//		}
//		
//		return new CompiledCode(tempCount + statementTempCount, instructions);
	}

	public CompiledMethod compileMethod(String methodSource) {
		List<String> tokens = lexer.lexify(methodSource);
		Method method = parser.parseMethod(tokens);
		
		throw new UnsupportedOperationException();
	}
	
//	private Pair<List<Instruction>, Integer> compile(ExecutableCode code, Statement stmt) {
//		// TODO fix this
//		StoreIntegerLiteralInstruction i3 = new StoreIntegerLiteralInstruction(0, 3);
//		StoreIntegerLiteralInstruction i5 = new StoreIntegerLiteralInstruction(1, 5);
//		SendMessageInstruction msg = new SendMessageInstruction(0, "+", 1, 2);
//		ReturnCodeInstruction ret = new ReturnCodeInstruction(2);
//		
//		List<Instruction> instructions = ListUtils.asList(i3, i5, msg, ret);
//		return Pair.of(instructions, 3);
//	}
}
