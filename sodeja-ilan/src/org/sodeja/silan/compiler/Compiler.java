package org.sodeja.silan.compiler;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.silan.CompiledCode;
import org.sodeja.silan.CompiledMethod;
import org.sodeja.silan.compiler.src.ExecutableCode;
import org.sodeja.silan.compiler.src.MethodDeclaration;
import org.sodeja.silan.compiler.src.Statement;
import org.sodeja.silan.compiler.src.Token;
import org.sodeja.silan.instruction.ClearStackInstruction;
import org.sodeja.silan.instruction.DuplicateStackInstruction;
import org.sodeja.silan.instruction.Instruction;
import org.sodeja.silan.instruction.MessageInstruction;
import org.sodeja.silan.instruction.PopReferenceInstruction;
import org.sodeja.silan.instruction.PushInstruction;
import org.sodeja.silan.instruction.ReturnCallerValueInstruction;
import org.sodeja.silan.instruction.ReturnCodeInstruction;
import org.sodeja.silan.instruction.ReturnSelfInstruction;
import org.sodeja.silan.instruction.ReturnValueInstruction;

public class Compiler {
	private final CompilerLexer lexer;
	private final CompilerParser parser;
	
	public Compiler() {
		this.lexer = new CompilerLexer();
		this.parser = new CompilerParser();
	}

	public CompiledCode compileCode(String codeSource) {
		List<Token> tokens = lexer.lexify(codeSource);
		ExecutableCode code = parser.parseCode(tokens);
		
		CompiledCode compiled = compileCode(code, CompileTargetType.CODE);
		compiled.setSource(codeSource);
		return compiled;
	}

	public CompiledCode compileCode(ExecutableCode code, CompileTargetType type) {
		int statementTempCount = 0;
		List<Instruction> instructions = new ArrayList<Instruction>();
		
		for(int i = 0, n = code.statements.size();i < n;i++) {
			Statement stmt = code.statements.get(i);
			
			int lastIndex = instructions.size();
			stmt.compile(this, code, instructions);
			
			int statementStackUsage = computeStackUsage(instructions, lastIndex);
			if(statementTempCount < statementStackUsage) {
				statementTempCount = statementStackUsage;
			}
			
			if(i == n - 1 && code.finalStatement == null) {
				addDefaultReturnInstruction(instructions, type);
			} else {
				instructions.add(new ClearStackInstruction());
			}
		}
		
		if(code.finalStatement != null) {
			int lastIndex = instructions.size();
			code.finalStatement.compile(this, code, instructions);

			int statementStackUsage = computeStackUsage(instructions, lastIndex);
			if(statementTempCount < statementStackUsage) {
				statementTempCount = statementStackUsage;
			}
			
			if(code.finalStatement.isExplicit()) {
				addExplicitReturnInstruction(instructions, type);
			} else {
				addDefaultReturnInstruction(instructions, type);
			}
		}
		
		return new CompiledCode(code.localVariables, statementTempCount, instructions);
	}
	
	private void addDefaultReturnInstruction(List<Instruction> instructions, CompileTargetType type) {
		if(type == CompileTargetType.CODE) {
			instructions.add(new ReturnCodeInstruction());
		} else if(type == CompileTargetType.METHOD) {
			instructions.add(new ReturnSelfInstruction());
		} else if(type == CompileTargetType.BLOCK) {
			if(instructions.get(instructions.size() - 1) instanceof PopReferenceInstruction) {
				instructions.add(instructions.size() - 1, new DuplicateStackInstruction());
			}
			instructions.add(new ReturnValueInstruction());
		} else {
			throw new RuntimeException();
		}
	}
	
	private void addExplicitReturnInstruction(List<Instruction> instructions, CompileTargetType type) {
		if(type == CompileTargetType.CODE) {
			instructions.add(new ReturnCodeInstruction());
		} else if(type == CompileTargetType.METHOD) {
			instructions.add(new ReturnValueInstruction());
		} else if(type == CompileTargetType.BLOCK) {
			instructions.add(new ReturnCallerValueInstruction());
		} else {
			throw new RuntimeException();
		}
	}
	
	public CompiledMethod compileMethod(String methodSource) {
		List<Token> tokens = lexer.lexify(methodSource);
		MethodDeclaration method = parser.parseMethod(tokens);
		
		CompiledCode code = compileCode(method.code, CompileTargetType.METHOD);
		
		CompiledMethod compiledMethod = new CompiledMethod(
				method.header.getSelector(), method.header.getArguments(), 
				code.localVariables, code.maxStackSize, code.instructions);
		compiledMethod.setSource(methodSource);
		return compiledMethod;
	}
	
	private int computeStackUsage(List<Instruction> instructions, int lastIndex) {
		int maxStackUsage = 0;
		int stackUsage = 0;
		for(int i = lastIndex, n = instructions.size();i < n;i++) {
			Instruction instr = instructions.get(i);
			if(instr instanceof PushInstruction) {
				stackUsage++;
				
				if(stackUsage > maxStackUsage) {
					maxStackUsage = stackUsage;
				}
			} else if(instr instanceof MessageInstruction) {
				stackUsage -= ((MessageInstruction) instr).getSize();
			}
		}
		return maxStackUsage;
	}
}
