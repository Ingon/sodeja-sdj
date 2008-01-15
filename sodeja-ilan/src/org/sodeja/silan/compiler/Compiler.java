package org.sodeja.silan.compiler;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.CollectionUtils;
import org.sodeja.functional.Pair;
import org.sodeja.silan.CompiledCode;
import org.sodeja.silan.CompiledMethod;
import org.sodeja.silan.compiler.src.BinaryMessage;
import org.sodeja.silan.compiler.src.BinaryMessageOperand;
import org.sodeja.silan.compiler.src.BinaryRootMessage;
import org.sodeja.silan.compiler.src.ExecutableCode;
import org.sodeja.silan.compiler.src.Expression;
import org.sodeja.silan.compiler.src.IntegerLiteral;
import org.sodeja.silan.compiler.src.KeywordMessage;
import org.sodeja.silan.compiler.src.KeywordMessageArgument;
import org.sodeja.silan.compiler.src.Message;
import org.sodeja.silan.compiler.src.MethodDeclaration;
import org.sodeja.silan.compiler.src.Primary;
import org.sodeja.silan.compiler.src.Reference;
import org.sodeja.silan.compiler.src.Statement;
import org.sodeja.silan.compiler.src.StringLiteral;
import org.sodeja.silan.compiler.src.Token;
import org.sodeja.silan.compiler.src.UnaryMessage;
import org.sodeja.silan.compiler.src.UnaryRootMessage;
import org.sodeja.silan.instruction.ClearStackInstruction;
import org.sodeja.silan.instruction.Instruction;
import org.sodeja.silan.instruction.MessageInstruction;
import org.sodeja.silan.instruction.PopReferenceInstruction;
import org.sodeja.silan.instruction.PushIntegerLiteralInstruction;
import org.sodeja.silan.instruction.PushReferenceInstruction;
import org.sodeja.silan.instruction.PushStringLiteralInstruction;
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
		
		CompiledCode compiled = compileCode(code, false);
		compiled.setSource(codeSource);
		return compiled;
	}

	private CompiledCode compileCode(ExecutableCode code, boolean isMethod) {
		int statementTempCount = 0;
		List<Instruction> instructions = new ArrayList<Instruction>();
		
		for(int i = 0, n = code.statements.size();i < n;i++) {
			Statement stmt = code.statements.get(i);
			Pair<List<Instruction>, Integer> result = compile(code, stmt);
			if(statementTempCount < result.second) {
				statementTempCount = result.second;
			}
			instructions.addAll(result.first);
			if(i == n - 1 && code.finalStatement == null) {
				if(isMethod) {
					instructions.add(new ReturnSelfInstruction());
				} else {
					instructions.add(new ReturnCodeInstruction());
				}
			} else {
				instructions.add(new ClearStackInstruction());
			}
		}
		
		if(code.finalStatement != null) {
			Pair<List<Instruction>, Integer> result = compile(code, code.finalStatement);
			if(statementTempCount < result.second) {
				statementTempCount = result.second;
			}
			instructions.addAll(result.first);
			instructions.add(new ReturnValueInstruction());
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
		
		tempCount++;
		instructions.add(compilePrimary(expression.primary));
		if(! expression.messages.isEmpty()) {
			Message message = expression.messages.get(0);
			if(message instanceof UnaryRootMessage) {
				UnaryRootMessage root = (UnaryRootMessage) message;
				if(root.keyword != null) {
					throw new UnsupportedOperationException("Does not supports keyword messages");
				}
				
				Pair<List<Instruction>, Integer> unaryInstructions = compileUnaryChain(root.unaries);
				instructions.addAll(unaryInstructions.first);
				
				if(! CollectionUtils.isEmpty(root.binaries)) {
					Pair<List<Instruction>, Integer> binInstructions = compileBinary(root.binaries);
					instructions.addAll(binInstructions.first);

					tempCount += 1;
				}
			} else if(message instanceof KeywordMessage) {
				KeywordMessage root = (KeywordMessage) message;
				for(KeywordMessageArgument arg : root.arguments) {
					instructions.add(compilePrimary(arg.primary));
	
					if(! CollectionUtils.isEmpty(arg.unaries)) {
						Pair<List<Instruction>, Integer> unaryInstructions = compileUnaryChain(arg.unaries);
						instructions.addAll(unaryInstructions.first);
					}
					
					if(! CollectionUtils.isEmpty(arg.binaries)) {
						Pair<List<Instruction>, Integer> binInstructions = compileBinary(arg.binaries);
						instructions.addAll(binInstructions.first);
					}
				}
				
				tempCount += root.arguments.size() + 1;
				instructions.add(new MessageInstruction(root.selector, root.arguments.size()));
			} else if(message instanceof BinaryRootMessage) {
				BinaryRootMessage root = (BinaryRootMessage) message;
				if(root.keyword != null) {
					throw new UnsupportedOperationException("Does not supports keyword messages");
				}
				
				Pair<List<Instruction>, Integer> binInstructions = compileBinary(root.binaries);
				instructions.addAll(binInstructions.first);
				tempCount += 1;
			}
		}
		
		return Pair.of(instructions, tempCount);
	}

	private Instruction compilePrimary(Primary primary) {
		if(primary instanceof IntegerLiteral) {
			return new PushIntegerLiteralInstruction(((IntegerLiteral) primary).value);
		} else if(primary instanceof StringLiteral) {
			return new PushStringLiteralInstruction(((StringLiteral) primary).value);
		} else if(primary instanceof Reference) {
			return new PushReferenceInstruction(((Reference) primary).value);
		}
		throw new UnsupportedOperationException();
	}
	
	private Pair<List<Instruction>, Integer> compileBinary(List<BinaryMessage> binaries) {
		List<Instruction> instructions = new ArrayList<Instruction>();
		for(BinaryMessage binary : binaries) {
			BinaryMessageOperand operand = binary.operand;
			instructions.add(compilePrimary(operand.primary));
			
			Pair<List<Instruction>, Integer> unary = compileUnaryChain(operand.unaries);
			instructions.addAll(unary.first);
			
			instructions.add(new MessageInstruction(binary.selector, 1));
//			instructions.add(new BinaryMessageInstruction(binary.selector));
		}
		
		return Pair.of(instructions, 2);
	}

	private Pair<List<Instruction>, Integer> compileUnaryChain(List<UnaryMessage> messages) {
		List<Instruction> instructions = new ArrayList<Instruction>();
		for(UnaryMessage msg : messages) {
			instructions.add(new MessageInstruction(msg.selector, 0));
//			instructions.add(new UnaryMessageInstruction(msg.selector));
		}
		return Pair.of(instructions, 0);
	}
	
	public CompiledMethod compileMethod(String methodSource) {
		List<Token> tokens = lexer.lexify(methodSource);
		MethodDeclaration method = parser.parseMethod(tokens);
		
		CompiledCode code = compileCode(method.code, true);
		
		CompiledMethod compiledMethod = new CompiledMethod(
				method.header.getSelector(), method.header.getArguments(), 
				code.localVariables, code.maxStackSize, code.instructions);
		compiledMethod.setSource(methodSource);
		return compiledMethod;
	}
}
