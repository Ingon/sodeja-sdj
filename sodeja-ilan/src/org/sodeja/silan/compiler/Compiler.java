package org.sodeja.silan.compiler;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.CollectionUtils;
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
import org.sodeja.silan.instruction.PushInstruction;
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
			
			int lastIndex = instructions.size();
			compile(code, stmt, instructions);
			
			int statementStackUsage = computeStackUsage(instructions, lastIndex);
			if(statementTempCount < statementStackUsage) {
				statementTempCount = statementStackUsage;
			}
			
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
			int lastIndex = instructions.size();
			compile(code, code.finalStatement, instructions);

			int statementStackUsage = computeStackUsage(instructions, lastIndex);
			if(statementTempCount < statementStackUsage) {
				statementTempCount = statementStackUsage;
			}
			
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

	private void compile(ExecutableCode code, Statement stmt, List<Instruction> instructions) {
		compile(code, stmt.expression, instructions);
		if(stmt.assignment != null) {
			instructions.add(new PopReferenceInstruction(stmt.assignment));
		}
	}

	private List<Instruction> compile(ExecutableCode codeModel, Expression expression, List<Instruction> instructions) {
		if(expression.messages.size() > 1) {
			throw new UnsupportedOperationException("Does not supports cascade compile");
		}
		
		compilePrimary(codeModel, expression.primary, instructions);
		
		if(! expression.messages.isEmpty()) {
			Message message = expression.messages.get(0);
			if(message instanceof UnaryRootMessage) {
				UnaryRootMessage root = (UnaryRootMessage) message;
				
				compileUnaryChain(codeModel, root.unaries, instructions);
				compileBinary(codeModel, root.binaries, instructions);
				compileKeyword(codeModel, root.keyword, instructions);
			} else if(message instanceof KeywordMessage) {
				KeywordMessage root = (KeywordMessage) message;

				compileKeyword(codeModel, root, instructions);
			} else if(message instanceof BinaryRootMessage) {
				BinaryRootMessage root = (BinaryRootMessage) message;
				
				compileBinary(codeModel, root.binaries, instructions);
				compileKeyword(codeModel, root.keyword, instructions);
			}
		}
		
		return instructions;
	}
	
	private void compilePrimary(ExecutableCode codeModel, Primary primary, List<Instruction> instructions) {
		if(primary instanceof NilLiteral) {
			instructions.add(new PushNilLiteralInstruction());
		} else if(primary instanceof BooleanLiteral) {
			instructions.add(new PushBooleanLiteralInstruction(((BooleanLiteral) primary).value));
		} else if(primary instanceof IntegerLiteral) {
			instructions.add(new PushIntegerLiteralInstruction(((IntegerLiteral) primary).value));
		} else if(primary instanceof StringLiteral) {
			instructions.add(new PushStringLiteralInstruction(((StringLiteral) primary).value));
		} else if(primary instanceof Reference) {
			instructions.add(new PushReferenceInstruction(((Reference) primary).value));
		} else if(primary instanceof BlockLiteral) {
			BlockLiteral blockLiteral = (BlockLiteral) primary;
			CompiledCode code = compileCode(blockLiteral.code, CompileTargetType.BLOCK);
			CompiledBlock block = new CompiledBlock(blockLiteral.argument, 
					code.localVariables, code.maxStackSize, code.instructions);
			instructions.add(new PushBlockInstruction(block));
		} else if(primary instanceof NestedExpression) {
			NestedExpression nested = (NestedExpression) primary;
			compile(codeModel, nested.statement, instructions);
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	private void compileUnaryChain(ExecutableCode codeModel, List<UnaryMessage> messages, List<Instruction> instructions) {
		if(CollectionUtils.isEmpty(messages)) {
			return;
		}
		
		for(UnaryMessage msg : messages) {
			instructions.add(new MessageInstruction(msg.selector, 0));
		}
	}
	
	private void compileBinary(ExecutableCode codeModel, List<BinaryMessage> messages, List<Instruction> instructions) {
		if(CollectionUtils.isEmpty(messages)) {
			return;
		}
		
		for(BinaryMessage binary : messages) {
			BinaryMessageOperand operand = binary.operand;
			compilePrimary(codeModel, operand.primary, instructions);
			compileUnaryChain(codeModel, operand.unaries, instructions);
			instructions.add(new MessageInstruction(binary.selector, 1));
		}
	}

	private void compileKeyword(ExecutableCode codeModel, KeywordMessage root, List<Instruction> instructions) {
		if(root == null) {
			return;
		}
		
		for(KeywordMessageArgument arg : root.arguments) {
			compilePrimary(codeModel, arg.primary, instructions);
			compileUnaryChain(codeModel, arg.unaries, instructions);
			compileBinary(codeModel, arg.binaries, instructions);
		}
		instructions.add(new MessageInstruction(root.selector, root.arguments.size()));
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
