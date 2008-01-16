package org.sodeja.silan.compiler;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.CollectionUtils;
import org.sodeja.functional.Pair;
import org.sodeja.silan.CompiledBlock;
import org.sodeja.silan.CompiledCode;
import org.sodeja.silan.CompiledMethod;
import org.sodeja.silan.compiler.src.BinaryMessage;
import org.sodeja.silan.compiler.src.BinaryMessageOperand;
import org.sodeja.silan.compiler.src.BinaryRootMessage;
import org.sodeja.silan.compiler.src.BlockLiteral;
import org.sodeja.silan.compiler.src.BooleanLiteral;
import org.sodeja.silan.compiler.src.ExecutableCode;
import org.sodeja.silan.compiler.src.Expression;
import org.sodeja.silan.compiler.src.IntegerLiteral;
import org.sodeja.silan.compiler.src.KeywordMessage;
import org.sodeja.silan.compiler.src.KeywordMessageArgument;
import org.sodeja.silan.compiler.src.Message;
import org.sodeja.silan.compiler.src.MethodDeclaration;
import org.sodeja.silan.compiler.src.NestedExpression;
import org.sodeja.silan.compiler.src.NilLiteral;
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
import org.sodeja.silan.instruction.PushBlockInstruction;
import org.sodeja.silan.instruction.PushBooleanLiteralInstruction;
import org.sodeja.silan.instruction.PushIntegerLiteralInstruction;
import org.sodeja.silan.instruction.PushNilLiteralInstruction;
import org.sodeja.silan.instruction.PushReferenceInstruction;
import org.sodeja.silan.instruction.PushStringLiteralInstruction;
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

	private CompiledCode compileCode(ExecutableCode code, CompileTargetType type) {
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
				if(type == CompileTargetType.CODE) {
					instructions.add(new ReturnCodeInstruction());
				} else if(type == CompileTargetType.METHOD) {
					instructions.add(new ReturnSelfInstruction());
				} else if(type == CompileTargetType.BLOCK) {
					instructions.add(new ReturnValueInstruction());
				} else {
					throw new RuntimeException();
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
		
		int tempCount = 1;
		List<Instruction> instructions = new ArrayList<Instruction>();
		Pair<List<Instruction>, Integer> subResult = null;
		
		if(expression.primary instanceof NestedExpression) {
			subResult = compile(code, ((NestedExpression) expression.primary).statement);
			instructions.addAll(subResult.first);
		} else {
			instructions.add(compilePrimary(expression.primary));
		}
		
		if(! expression.messages.isEmpty()) {
			Message message = expression.messages.get(0);
			if(message instanceof UnaryRootMessage) {
				UnaryRootMessage root = (UnaryRootMessage) message;
				if(root.keyword != null) {
					throw new UnsupportedOperationException("Does not supports keyword messages");
				}
				
				List<Instruction> unaryInstructions = compileUnaryChain(root.unaries);
				instructions.addAll(unaryInstructions);
				
				if(! CollectionUtils.isEmpty(root.binaries)) {
					List<Instruction> binInstructions = compileBinary(root.binaries);
					instructions.addAll(binInstructions);

					tempCount++;
				}
			} else if(message instanceof KeywordMessage) {
				KeywordMessage root = (KeywordMessage) message;
				
				instructions.addAll(compileKeyword(root));
				tempCount += root.arguments.size() + 1;
			} else if(message instanceof BinaryRootMessage) {
				BinaryRootMessage root = (BinaryRootMessage) message;
				
				List<Instruction> binInstructions = compileBinary(root.binaries);
				instructions.addAll(binInstructions);
				tempCount++;
				
				if (root.keyword != null) {
					instructions.addAll(compileKeyword(root.keyword));
					tempCount = Math.max(tempCount, root.keyword.arguments.size() + 2);
				}
			}
		}
		
		if(subResult != null) {
			tempCount = Math.max(tempCount, subResult.second);
		}
		
		return Pair.of(instructions, tempCount);
	}
	
	private List<Instruction> compileKeyword(KeywordMessage root) {
		List<Instruction> instructions = new ArrayList<Instruction>();
		for(KeywordMessageArgument arg : root.arguments) {
			instructions.add(compilePrimary(arg.primary));

			if(! CollectionUtils.isEmpty(arg.unaries)) {
				List<Instruction> unaryInstructions = compileUnaryChain(arg.unaries);
				instructions.addAll(unaryInstructions);
			}
			
			if(! CollectionUtils.isEmpty(arg.binaries)) {
				List<Instruction> binInstructions = compileBinary(arg.binaries);
				instructions.addAll(binInstructions);
			}
		}
		instructions.add(new MessageInstruction(root.selector, root.arguments.size()));
		return instructions;
	}

	private Instruction compilePrimary(Primary primary) {
		if(primary instanceof NilLiteral) {
			return new PushNilLiteralInstruction();
		} else if(primary instanceof BooleanLiteral) {
			return new PushBooleanLiteralInstruction(((BooleanLiteral) primary).value);
		} else if(primary instanceof IntegerLiteral) {
			return new PushIntegerLiteralInstruction(((IntegerLiteral) primary).value);
		} else if(primary instanceof StringLiteral) {
			return new PushStringLiteralInstruction(((StringLiteral) primary).value);
		} else if(primary instanceof Reference) {
			return new PushReferenceInstruction(((Reference) primary).value);
		} else if(primary instanceof BlockLiteral) {
			BlockLiteral blockLiteral = (BlockLiteral) primary;
			CompiledCode code = compileCode(blockLiteral.code, CompileTargetType.BLOCK);
			CompiledBlock block = new CompiledBlock(blockLiteral.argument, 
					code.localVariables, code.maxStackSize, code.instructions);
			return new PushBlockInstruction(block);
		}
		throw new UnsupportedOperationException();
	}
	
	private List<Instruction> compileBinary(List<BinaryMessage> binaries) {
		List<Instruction> instructions = new ArrayList<Instruction>();
		for(BinaryMessage binary : binaries) {
			BinaryMessageOperand operand = binary.operand;
			instructions.add(compilePrimary(operand.primary));
			
			List<Instruction> unary = compileUnaryChain(operand.unaries);
			instructions.addAll(unary);
			
			instructions.add(new MessageInstruction(binary.selector, 1));
		}
		
		return instructions;
	}

	private List<Instruction> compileUnaryChain(List<UnaryMessage> messages) {
		List<Instruction> instructions = new ArrayList<Instruction>();
		for(UnaryMessage msg : messages) {
			instructions.add(new MessageInstruction(msg.selector, 0));
		}
		return instructions;
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
}
