package org.sodeja.silan.compiler;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.CollectionUtils;
import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Pair;
import org.sodeja.silan.ClearStackInstruction;
import org.sodeja.silan.CompiledCode;
import org.sodeja.silan.CompiledMethod;
import org.sodeja.silan.Instruction;
import org.sodeja.silan.PopReferenceInstruction;
import org.sodeja.silan.PushReferenceInstruction;
import org.sodeja.silan.ReturnCodeInstruction;
import org.sodeja.silan.BinaryMessageInstruction;
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
		List<Token> tokens = lexer.lexify(codeSource);
		ExecutableCode code = parser.parseCode(tokens);
		if(code.finalStatement != null) {
			throw new UnsupportedOperationException("Does not supports explicit return");
		}
		
		int statementTempCount = 0;
		List<Instruction> instructions = new ArrayList<Instruction>();
		
		for(int i = 0, n = code.statements.size();i < n;i++) {
			Statement stmt = code.statements.get(i);
			Pair<List<Instruction>, Integer> result = compile(code, stmt);
			if(statementTempCount < result.second) {
				statementTempCount = result.second;
			}
			instructions.addAll(result.first);
			if(i == n - 1) {
				instructions.add(new ReturnCodeInstruction());
			} else {
				instructions.add(new ClearStackInstruction());
			}
		}
		
		return new CompiledCode(code.localVariables, statementTempCount, instructions);
	}

	private Pair<List<Instruction>, Integer> compile(ExecutableCode code, Statement stmt) {
		Pair<List<Instruction>, Integer> result = compile(code, stmt.expression);
		if(stmt.assignment != null) {
			result.first.add(new PopReferenceInstruction(stmt.assignment));
		}
		return result;
	}

	private Pair<List<Instruction>, Integer> compile(ExecutableCode code, Expression expression) {
		if(expression.messages.size() > 1) {
			throw new UnsupportedOperationException("Does not supports cascade compile");
		}
		
		int tempCount = 0;
		List<Instruction> instructions = new ArrayList<Instruction>();
		
		instructions.add(compilePrimary(expression.primary, tempCount++));
		
		Message message = expression.messages.get(0);
		if(message instanceof UnaryRootMessage) {
			throw new UnsupportedOperationException("Does not supports unary messages");
		} else if(message instanceof KeywordMessage) {
			throw new UnsupportedOperationException("Does not supports keyword messages");
		} else if(message instanceof BinaryRootMessage) {
			BinaryRootMessage root = (BinaryRootMessage) message;
			if(root.binaries.size() > 1) {
				throw new UnsupportedOperationException("Does not supports muliple binary messages");
			}
			if(root.keyword != null) {
				throw new UnsupportedOperationException("Does not supports keyword messages");
			}
			
			BinaryMessage binary = root.binaries.get(0);
			BinaryMessageOperand operand = binary.operand;
			if(! CollectionUtils.isEmpty(operand.unaries)) {
				throw new UnsupportedOperationException("Does not supports multiple binary messages");
			}
			
			instructions.add(compilePrimary(operand.primary, tempCount++));
			tempCount++;
			instructions.add(new BinaryMessageInstruction(binary.selector));
		}
		
		return Pair.of(instructions, tempCount);
	}

	private Instruction compilePrimary(Primary primary, int location) {
		if(primary instanceof IntegerLiteral) {
			return new StoreIntegerLiteralInstruction(((IntegerLiteral) primary).value);
		} else if(primary instanceof Reference) {
			return new PushReferenceInstruction(((Reference) primary).value);
		}
		throw new UnsupportedOperationException();
	}
	
	public CompiledMethod compileMethod(String methodSource) {
		List<Token> tokens = lexer.lexify(methodSource);
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
