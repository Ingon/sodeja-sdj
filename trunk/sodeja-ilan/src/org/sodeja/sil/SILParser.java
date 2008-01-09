package org.sodeja.sil;

import static org.sodeja.parsec2.ParserCombinators.apply;
import static org.sodeja.parsec2.ParserCombinators.matchUnless;
import static org.sodeja.parsec2.ParserCombinators.optional;
import static org.sodeja.parsec2.ParserCombinators.optionalThen;
import static org.sodeja.parsec2.ParserCombinators.or;
import static org.sodeja.parsec2.ParserCombinators.repeated;
import static org.sodeja.parsec2.ParserCombinators.repeatedThen;
import static org.sodeja.parsec2.ParserCombinators.then;
import static org.sodeja.parsec2.PrimitiveParsers.anyCharacter;
import static org.sodeja.parsec2.PrimitiveParsers.character;
import static org.sodeja.parsec2.PrimitiveParsers.oneOfCharacters;
import static org.sodeja.parsec2.PrimitiveParsers.string;
import static org.sodeja.parsec2.PrimitiveParsers.stringAsString;
import static org.sodeja.parsec2.PrimitiveParsers.whitespaceCharacter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.parsec2.DelegateParser;
import org.sodeja.parsec2.Parser;
import org.sodeja.sil.model.Assignment;
import org.sodeja.sil.model.BinaryMessage;
import org.sodeja.sil.model.BinaryMessageChain;
import org.sodeja.sil.model.BinaryMessageOperand;
import org.sodeja.sil.model.BinaryMessageSelector;
import org.sodeja.sil.model.BinaryMethodHeader;
import org.sodeja.sil.model.BlockArgument;
import org.sodeja.sil.model.BooleanReference;
import org.sodeja.sil.model.CascadedMessage;
import org.sodeja.sil.model.Comment;
import org.sodeja.sil.model.DefaultStatement;
import org.sodeja.sil.model.ExecutableCode;
import org.sodeja.sil.model.Expression;
import org.sodeja.sil.model.Identifier;
import org.sodeja.sil.model.Keyword;
import org.sodeja.sil.model.KeywordMessage;
import org.sodeja.sil.model.KeywordMessageArgument;
import org.sodeja.sil.model.KeywordMessageSegment;
import org.sodeja.sil.model.KeywordMessageSelector;
import org.sodeja.sil.model.KeywordMethodHeader;
import org.sodeja.sil.model.KeywordMethodHeaderSegment;
import org.sodeja.sil.model.MessageChain;
import org.sodeja.sil.model.MessageChainBinary;
import org.sodeja.sil.model.MessageChainUnary;
import org.sodeja.sil.model.MethodDeclaration;
import org.sodeja.sil.model.MethodHeader;
import org.sodeja.sil.model.NestedExpression;
import org.sodeja.sil.model.NilReference;
import org.sodeja.sil.model.Operand;
import org.sodeja.sil.model.Reference;
import org.sodeja.sil.model.ReturnStatement;
import org.sodeja.sil.model.Statement;
import org.sodeja.sil.model.UnaryMessage;
import org.sodeja.sil.model.UnaryMessageChain;
import org.sodeja.sil.model.UnaryMessageSelector;
import org.sodeja.sil.model.UnaryMethodHeader;
import org.sodeja.sil.model.literal.BlockLiteral;
import org.sodeja.sil.model.literal.IntegerLiteral;
import org.sodeja.sil.model.literal.LiteralArrayElement;
import org.sodeja.sil.model.literal.ObjectArrayLiteral;

public class SILParser {
	private static final SILParser instance = new SILParser();
	
	public static SILParser getInstance() {
		return instance;
	}
	
	private final Parser executableCodeParser;
	private final Parser methodParser;
	
	@SuppressWarnings("unchecked")
	private SILParser() {
		Parser anyChar = anyCharacter();
		Parser whitespaceChar = whitespaceCharacter();
		
		Parser decimalDigit = oneOfCharacters("0123456789");
		Parser letter = oneOfCharacters("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
		
		Parser commentChar_t = matchUnless(anyChar, character('"'));
		Parser commentChars = apply(repeated(commentChar_t), new Function1() {
			@Override
			public Object execute(Object p) {
				return extractString(p);
			}});
		
		Parser comment_t = then("comment", character('"'), commentChars, character('"'));
		Parser comment = apply(comment_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List vals = (List) p;
				String commentChars = (String) vals.get(1);
				return new Comment(commentChars);
			}});
		
		Parser optionalWhitespace_t = repeated(or(whitespaceChar, comment));
		Parser optionalWhitespace = apply(optionalWhitespace_t, new Function1() {
			@Override
			public Object execute(Object p) {
				return null;
			}});
		Parser whitespace = then("whitespace", or(whitespaceChar, comment), optionalWhitespace);
		
		Parser letterOrDigit = or("letterOrDigit", decimalDigit, letter);
		Parser identifier_t = then("identifier", or(letter, character('_')), repeated(or(letterOrDigit, character('_'))));
		Parser identifier = apply(identifier_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List vals = (List) p;
				Character start = (Character) vals.get(0);
				List cont = vals.size() < 2 ? Collections.emptyList() : (List) vals.get(1);
				return new Identifier(start + extractString(cont));
			}});
		
		
//		Parser reference_t = identifier;
		Parser reference = apply(identifier, new Function1() {
			@Override
			public Object execute(Object p) {
				return new Reference((Identifier) p);
			}});
		
		Parser nilConstantReference = apply(stringAsString("nil"), new Function1() {
			@Override
			public Object execute(Object p) {
				return NilReference.getInstance();
			}});
		Parser falseConstantReference = apply(stringAsString("false"), new Function1() {
			@Override
			public Object execute(Object p) {
				return BooleanReference.getFalseInstance();
			}});
		Parser trueConstantReference = apply(stringAsString("true"), new Function1() {
			@Override
			public Object execute(Object p) {
				return BooleanReference.getTrueInstance();
			}});
		Parser constantReference = or("constantReference", 
				nilConstantReference, 
				falseConstantReference, 
				trueConstantReference);
		
		Parser pseudoVariableReference = or("pseudoVariableReference", string("self"), string("super"), string("thisContext"));
		Parser reservedIdentifier = or("reservedIdentifier", pseudoVariableReference, constantReference);
		
		Parser bindableIdentifier = matchUnless(identifier, reservedIdentifier);
		
		Parser unaryMessageSelector = apply(identifier, new Function1() {
			@Override
			public Object execute(Object p) {
				return new UnaryMessageSelector((Identifier) p);
			}});
		
		Parser keyword_t = then("keyword", identifier, character(':'));
		Parser keyword = apply(keyword_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List vals = (List) p;
				return new Keyword((Identifier) vals.get(0));
			}});
		Parser keywordMessageSelector_t = then("keywordMessageSelector", keyword, repeated(keyword));
		Parser keywordMessageSelector = apply(keywordMessageSelector_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List vals = (List) p;
				Keyword start = (Keyword) vals.get(0);
				List<Keyword> cont = vals.size() == 2 ? (List<Keyword>) vals.get(1) : null;
				return new KeywordMessageSelector(start, cont);
			}});
		
		Parser binarySelectorChar = oneOfCharacters("~!@%&*-+=|\\<>,?/");
		Parser binaryMessageSelector_t = then("binaryMessageSelector", binarySelectorChar, optional(binarySelectorChar));
		Parser binaryMessageSelector = apply(binaryMessageSelector_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List vals = (List) p;
				StringBuilder sb = new StringBuilder();
				sb.append((Character) vals.get(0));
				if(vals.size() == 2) {
					sb.append((Character) vals.get(1));
				}
				return new BinaryMessageSelector(sb.toString());
			}});
		
		Parser decimalIntegerLiteral_t = then("decimalIntegerLiteral", decimalDigit, repeated(decimalDigit));
		Parser decimalIntegerLiteral = apply(decimalIntegerLiteral_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List vals = (List) p;
				StringBuilder sb = new StringBuilder();
				sb.append((Character) vals.get(0));
				if(vals.size() > 1) {
					List ivals = (List) vals.get(1);
					for(int i = 0;i < ivals.size();i++) {
						sb.append((Character) ivals.get(i));
					}
				}
				
				return new Integer(sb.toString());
			}});
		// TODO currently match only 4343 literals
		Parser unsignedIntegerLiteral = decimalIntegerLiteral; 
		Parser integerLiteral_t = then("integerLiteral", optional(character('-')), unsignedIntegerLiteral);
		Parser integerLiteral = apply(integerLiteral_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List vals = (List) p;
				if(vals.size() == 1) {
					return new IntegerLiteral((Integer) vals.get(0));
				}
				int val = (Integer) vals.get(0);
				return new IntegerLiteral(-val);
			}});
		
		Parser characterLiteral = then("characterLiteral", character('$'), anyChar);
		
		Parser stringLiteralCharacter = matchUnless(anyChar, character('\''));
		Parser stringLiteral = then("stringLiteral", character('\''), stringLiteralCharacter, character('\''));
		
		Parser symbolInArrayLiteral = or("symbolInArrayLiteral", matchUnless(unaryMessageSelector, constantReference), keywordMessageSelector, binaryMessageSelector);
		Parser symbolLiteral = then("symbolLiteral", character('#'), or(symbolInArrayLiteral, constantReference, stringLiteral));
		
		DelegateParser objectArrayLiteral = new DelegateParser("objectArrayLiteral");
		DelegateParser byteArrayLiteral = new DelegateParser("byteArrayLiteral");
		Parser arrayLiteral = or("arrayLiteral", objectArrayLiteral, byteArrayLiteral);
		
		DelegateParser literalArrayElement = new DelegateParser("literalArrayElement");
		Parser nestedObjectArrayLitera_in_t = optionalThen(literalArrayElement, repeatedThen(optionalWhitespace, literalArrayElement));
		Parser nestedObjectArrayLitera_in = apply(nestedObjectArrayLitera_in_t, new Function1() {
			@Override
			public Object execute(Object p) {
				if(p == null) {
					return null;
				}
				List vals = (List) p;
				if(vals.size() == 1) {
					return vals;
				}
				
				List ivals = (List) vals.get(1);
				
				List result = new ArrayList();
				result.add(vals.get(0));
				result.addAll(ivals);
				
				return result;
			}});
		Parser nestedObjectArrayLiteral_t = then("nestedObjectArrayLiteral", character('('), optionalWhitespace, nestedObjectArrayLitera_in, optionalWhitespace, character(')'));
		Parser nestedObjectArrayLiteral = apply(nestedObjectArrayLiteral_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List vals = (List) p;
				if(vals.size() == 2) {
					return new ObjectArrayLiteral(null);
				}
				return new ObjectArrayLiteral((List<LiteralArrayElement>) vals.get(1));
			}});
		
		Parser objectArrayLiteral_t = then("objectArrayLiteral", character('#'), nestedObjectArrayLiteral);
		objectArrayLiteral.delegate = apply(objectArrayLiteral_t, new Function1() {
			@Override
			public Object execute(Object p) {
				return ((List) p).get(1);
			}});
		byteArrayLiteral.delegate = then("byteArrayLiteral", string("#["), optionalWhitespace, optionalThen(unsignedIntegerLiteral, repeatedThen(whitespace, unsignedIntegerLiteral)), optionalWhitespace, character(']'));
		
		DelegateParser literal = new DelegateParser("literal");
		DelegateParser blockLiteral = new DelegateParser("blockLiteral");
		literalArrayElement.delegate = or("literalArrayElement", matchUnless(literal , blockLiteral), nestedObjectArrayLiteral, symbolInArrayLiteral, constantReference);
		
		Parser formalBlockArgumentDeclaration_t = then("formalBlockArgumentDeclaration", character(':'), bindableIdentifier);
		Parser formalBlockArgumentDeclaration = apply(formalBlockArgumentDeclaration_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List vals = (List) p;
				return new BlockArgument((Identifier) vals.get(1));
			}});
		Parser formalBlockArgumentDeclarationList_t = then("formalBlockArgumentDeclarationList", formalBlockArgumentDeclaration, repeatedThen(whitespace, formalBlockArgumentDeclaration));
		Parser formalBlockArgumentDeclarationList = apply(formalBlockArgumentDeclarationList_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List vals = (List) p;
				List result = new ArrayList();
				result.add(vals.get(0));
				
				if(vals.size() > 1) {
					result.addAll(ListUtils.map((List) vals.get(1), new Function1() {
						@Override
						public Object execute(Object p) {
							List vals = (List) p;
							return vals.get(1);
						}}));
				}
				return result;
			}});
		
		DelegateParser executableCode = new DelegateParser("executableCode");
		Parser blockLiteral_t = then("blockLiteral", character('['), optionalThen(optionalWhitespace, formalBlockArgumentDeclarationList, optionalWhitespace, character('|')), executableCode, optionalWhitespace, character(']'));
		blockLiteral.delegate = apply(blockLiteral_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List vals = (List) p;
				if(vals.size() == 3) {
					return new BlockLiteral(null, (ExecutableCode) vals.get(1));
				}
				List ivals = (List) vals.get(1);
				return new BlockLiteral((List<BlockArgument>) ivals.get(0), (ExecutableCode) vals.get(2));
			}});
		// TODO does not matches ScaledDecimalLiteral and FloatingPointLiteral
		literal.delegate = or("literal", constantReference, integerLiteral, characterLiteral, stringLiteral, symbolLiteral, arrayLiteral, blockLiteral);
		
		DelegateParser statement = new DelegateParser("statement");
		Parser nestedExpression_t = then("nestedExpression", character('('), statement , optionalWhitespace, character(')'));
		Parser nestedExpression = apply(nestedExpression_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List vals = (List) p;
				return new NestedExpression((Statement) vals.get(1));
			}});
		
		Parser operand = or("operand", literal, reference, nestedExpression);
		
		Parser unaryMessage_t = unaryMessageSelector;
		Parser unaryMessage = apply(unaryMessage_t, new Function1() {
			@Override
			public Object execute(Object p) {
				return new UnaryMessage((UnaryMessageSelector) p);
			}});
		Parser unaryMessageChain_t = repeatedThen(optionalWhitespace, unaryMessage);
		Parser unaryMessageChain = apply(unaryMessageChain_t, new Function1() {
			@Override
			public Object execute(Object p) {
				if(p == null) {
					return null;
				}
				return new UnaryMessageChain((List<UnaryMessage>) p);
			}});
		
		Parser binaryMessageOperand_t = then("binaryMessageOperand", operand, unaryMessageChain);
		Parser binaryMessageOperand = apply(binaryMessageOperand_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List vals = (List) p;
				if(vals.size() == 1) {
					return new BinaryMessageOperand((Operand) vals.get(0), null);
				}
				return new BinaryMessageOperand((Operand) vals.get(0), (UnaryMessageChain) vals.get(1));
			}});
		
		Parser binaryMessage_t = then("binaryMessage", binaryMessageSelector, optionalWhitespace, binaryMessageOperand);
		Parser binaryMessage = apply(binaryMessage_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List vals = (List) p;
				return new BinaryMessage((BinaryMessageSelector) vals.get(0), (BinaryMessageOperand) vals.get(1));
			}});
		
		Parser binaryMessageChain_t = repeatedThen(optionalWhitespace, binaryMessage);
		Parser binaryMessageChain = apply(binaryMessageChain_t, new Function1() {
			@Override
			public Object execute(Object p) {
				return new BinaryMessageChain((List<BinaryMessage>) p);
			}});
		
		Parser keywordMessageArgument_t = then("keywordMessageArgument", binaryMessageOperand, binaryMessageChain);
		Parser keywordMessageArgument = apply(keywordMessageArgument_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List vals = (List) p;
				return new KeywordMessageArgument((BinaryMessageOperand) vals.get(0), (BinaryMessageChain) vals.get(1));
			}});
		
		Parser keywordMessageSegment_t = then("keywordMessageSegment", keyword, optionalWhitespace, keywordMessageArgument);
		Parser keywordMessageSegment = apply(keywordMessageSegment_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List vals = (List) p;
				return new KeywordMessageSegment((Keyword) vals.get(0), (KeywordMessageArgument) vals.get(1));
			}});
		
		Parser keywordMessage_t = then("keywordMessage", keywordMessageSegment, repeatedThen(optionalWhitespace, keywordMessageSegment));
		Parser keywordMessage = apply(keywordMessage_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List vals = (List) p;
				if(vals.size() == 1) {
					return new KeywordMessage((KeywordMessageSegment) vals.get(0), null);
				}
				List ivals = (List) vals.get(1);
				List rvals = ListUtils.map(ivals, new Function1() {
					@Override
					public Object execute(Object p) {
						List vals = (List) p;
						return vals.get(0);
					}});
				
				return new KeywordMessage((KeywordMessageSegment) vals.get(0), rvals);
			}});
		
		Parser messageChain_t_unary = apply(then("messageChainUnary", unaryMessage, unaryMessageChain, binaryMessageChain, optional(keywordMessage)), 
				new Function1() {
					@Override
					public Object execute(Object p) {
						List vals = (List) p;
						
						if(vals.size() == 2) {
							return new MessageChainUnary((UnaryMessage) vals.get(0), null, (BinaryMessageChain) vals.get(1), null);
						}
						UnaryMessage um = (UnaryMessage) vals.get(0);
						UnaryMessageChain umc = (UnaryMessageChain) vals.get(1);
						BinaryMessageChain bmc = (BinaryMessageChain) (vals.size() == 3 ? vals.get(2) : null);
						KeywordMessage km = (KeywordMessage) (vals.size() == 4 ? vals.get(3) : null);
						return new MessageChainUnary(um, umc, bmc, km);
					}});
		
		Parser messageChain_t_binary = apply(then("messageChain_t_binary", binaryMessage, binaryMessageChain, optional(keywordMessage)), 
				new Function1() {
					@Override
					public Object execute(Object p) {
						List vals = (List) p;
						
						BinaryMessage bm = (BinaryMessage) vals.get(0);
						BinaryMessageChain bmc = (BinaryMessageChain) vals.get(1);
						KeywordMessage km = (KeywordMessage) (vals.size() == 3 ? vals.get(2) : null);
						return new MessageChainBinary(bm, bmc, km);
					}});
		
		Parser messageChain = or("messageChain", keywordMessage, messageChain_t_binary, messageChain_t_unary);
		Parser cascadedMessage = then("cascadedMessage", character(';'), optionalWhitespace, messageChain);
		
		Parser expression_t = then("expression", operand, optionalThen(optionalWhitespace, messageChain, repeatedThen(optionalWhitespace, cascadedMessage)));
		Parser expression = apply(expression_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List vals = (List) p;
				
				Operand operand = (Operand) vals.get(0);
				MessageChain chain = null;
				List<CascadedMessage> cascade = null;
				if(vals.size() == 2) {
					List ovals = (List) vals.get(1);
					chain = (MessageChain) ovals.get(0);
					cascade = (List<CascadedMessage>) (ovals.size() == 2 ? ovals.get(1) : null);
				}
				return new Expression(operand, chain, cascade);
			}});
		
		Parser assignmentOperation_t = then("assignmentOperation", optionalWhitespace, bindableIdentifier, optionalWhitespace, string(":="));
		Parser assignmentOperation = apply(assignmentOperation_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List lst = (List) p;
				return lst.get(0);
			}});
		
		Parser statement_t = then("statement", repeated(assignmentOperation), optionalWhitespace, expression);
		statement.delegate = apply(statement_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List lst = (List) p;
				if(lst.size() == 1) {
					return new DefaultStatement(null, (Expression) lst.get(0));
				}
				return new DefaultStatement((List<Assignment>) lst.get(0), (Expression) lst.get(1));
			}});
		
		Parser methodReturnOperator = then("methodReturnOperator", optionalWhitespace, character('^'));
		Parser finalStatement_t = then("finalStatement", optional(methodReturnOperator), statement);
		Parser finalStatement = apply(finalStatement_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List lst = (List) p;
				if(lst.size() == 1) {
					return lst.get(0);
				}
				return new ReturnStatement((DefaultStatement) lst.get(1));
			}});
		
		Parser localVariableDeclarationList_t = then("localVariableDeclarationList", optionalWhitespace, character('|'), optionalWhitespace, optionalThen(bindableIdentifier, repeatedThen(whitespace, bindableIdentifier)), optionalWhitespace, character('|'));
		Parser localVariableDeclarationList = apply(localVariableDeclarationList_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List lst = (List) p;
				if(lst.size() == 2) {
					return null;
				}
				return lst.get(1);
			}});

		Parser singleStatement = apply(then(statement, optionalWhitespace, character('.')), new Function1() {
			@Override
			public Object execute(Object p) {
				return ((List) p).get(0);
			}});
		Parser statements_t = optionalThen(repeated(singleStatement), optionalThen(finalStatement, optional(character('.'))));
		Parser statements = apply(statements_t, new Function1() {
			@Override
			public Object execute(Object p) {
				if(p == null) {
					return null;
				}
				
				List vals = (List) p;
				if(vals.size() == 1) {
					return vals.get(0);
				}
				if(vals.size() == 2) {
					return ListUtils.asList(vals.get(0));
				}
				
				List ivals = (List) vals.get(0);
				List result = new ArrayList(ivals);
				result.add(vals.get(1));
				
				return result;
			}});
		Parser executableCode_t = then("executableCode", optional(localVariableDeclarationList), statements); 
		executableCode.delegate = apply(executableCode_t, new Function1() {
			@Override
			public Object execute(Object p) {
				if(p == null) {
					return null;
				}
				List vals = (List) p;
				if(vals.size() == 1) {
					List ivals = (List) vals.get(0);
					if(ivals.get(0) instanceof Identifier) {
						return new ExecutableCode(ivals, null);
					}
					return new ExecutableCode(null, ivals);
				}
				return new ExecutableCode((List<Identifier>) vals.get(0), (List<Statement>) vals.get(1));
			}});
		this.executableCodeParser = executableCode;
		
		Parser unaryMethodHeader = apply(unaryMessageSelector, new Function1() {
			@Override
			public Object execute(Object p) {
				return new UnaryMethodHeader((UnaryMessageSelector) p);
			}});
		
		Parser binaryMethodHeader_t = then("binaryMethodHeader", binaryMessageSelector, optionalWhitespace, bindableIdentifier);
		Parser binaryMethodHeader = apply(binaryMethodHeader_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List vals = (List) p;
				return new BinaryMethodHeader((BinaryMessageSelector) vals.get(0), (Identifier) vals.get(1));
			}});
		
		Parser keywordMethodHeaderSegment_t = then("keywordMethodHeaderSegment", keyword, optionalWhitespace, bindableIdentifier);
		Parser keywordMethodHeaderSegment = apply(keywordMethodHeaderSegment_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List vals = (List) p;
				return new KeywordMethodHeaderSegment((Keyword) vals.get(0), (Identifier) vals.get(1));
			}});
		
		Parser keywordMethodHeader_t = then("keywordMethodHeader", keywordMethodHeaderSegment, repeatedThen(whitespace, keywordMethodHeaderSegment));
		Parser keywordMethodHeader = apply(keywordMethodHeader_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List vals = (List) p;
				if(vals.size() == 1) {
					return vals;
				}
				
				List segments = new ArrayList();
				segments.add(vals.get(0));
				List ivals = (List) vals.get(1);
				List rvals = ListUtils.map(ivals, new Function1() {
					@Override
					public Object execute(Object p) {
						List vals = (List) p;
						return vals.get(1);
					}});
				segments.addAll(rvals);
				
				return new KeywordMethodHeader(segments);
			}});
		
		Parser methodHeader = or("methodHeader", unaryMethodHeader, binaryMethodHeader, keywordMethodHeader);

		Parser methodDeclaration_t = then("methodDeclaration", optionalWhitespace, methodHeader, executableCode);
		Parser methodDeclaration = apply(methodDeclaration_t, new Function1() {
			@Override
			public Object execute(Object p) {
				List vals = (List) p;
				return new MethodDeclaration((MethodHeader) vals.get(0), (ExecutableCode) vals.get(1));
			}});
		
		this.methodParser = methodDeclaration;
	}

	@SuppressWarnings("unchecked")
	private static String extractString(Object p) {
		List vals = (List) p;
		char[] chs = new char[vals.size()];
		for(int i = 0;i < chs.length;i++) {
			chs[i] = (Character) vals.get(i);
		}
		return new String(chs);
	}

	public Parser getExecutableCodeParser() {
		return executableCodeParser;
	}

	public Parser getMethodParser() {
		return methodParser;
	}
}
