package org.sodeja.sdj.parser;

import java.util.List;

import org.sodeja.functional.Function1;
import org.sodeja.functional.Function2;
import org.sodeja.functional.Function3;
import org.sodeja.functional.Function4;
import org.sodeja.functional.Pair;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.combinator.DelegateParser;
import org.sodeja.parsec.semantic.AbstractSemanticParser;
import org.sodeja.sdj.expression.Alternative;
import org.sodeja.sdj.expression.Application;
import org.sodeja.sdj.expression.BinaryOperator;
import org.sodeja.sdj.expression.Case;
import org.sodeja.sdj.expression.Constructor;
import org.sodeja.sdj.expression.Definition;
import org.sodeja.sdj.expression.Expression;
import org.sodeja.sdj.expression.Lambda;
import org.sodeja.sdj.expression.Let;
import org.sodeja.sdj.expression.Name;
import org.sodeja.sdj.expression.Number;
import org.sodeja.sdj.expression.Operator;
import org.sodeja.sdj.expression.Program;
import org.sodeja.sdj.expression.Supercombinator;
import org.sodeja.sdj.expression.Variable;

import static org.sodeja.parsec.combinator.ParsecUtils.*;
import static org.sodeja.parsec.standart.StandartParsers.*;

public class SdjParser extends AbstractSemanticParser<String, Program<Name>>{
	
	public DelegateParser<String, Expression<Name>> EXPRESSION_PARSER = 
		new DelegateParser<String, Expression<Name>>("EXPRESSION_PARSER");
	
	public DelegateParser<String, Expression<Name>> EXPRESSION_PARSER_NOAPPLY = 
		new DelegateParser<String, Expression<Name>>("EXPRESSION_PARSER");
	
	public DelegateParser<String, Expression<Name>> ATOMIC_EXPRESSION_PARSER = 
		new DelegateParser<String, Expression<Name>>("ATOMIC_EXPRESSION_PARSER");
	
	public Parser<String, Name> NAME_PARSER = 
		apply("NAME_PARSER_apply", new PVar("NAME_PARSER"), 
			new Function1<Name, String>() {
				public Name execute(String p) {
					return new Name(p);
				}
			}
		);
	
	public Parser<String, List<Name>> NAMES_PARSER = 
		zeroOrMore("NAMES_PARSER", NAME_PARSER);
	
	public Parser<String, Number<Name>> NUMBER_PARSER = 
		apply("NUMBER_PARSER_apply", simpleIntegerParser("NUMBER_PARSER"), 
			new Function1<Number<Name>, Integer> () {
				public Number<Name> execute(Integer p) {
					return new Number<Name>(p);
				}
			}
		);
	
	public Parser<String, Variable<Name>> VARIABLE_PARSER = 
		apply("VARIABLE_PARSER", NAME_PARSER,
			new Function1<Variable<Name>, Name>() {
				public Variable<Name> execute(Name p) {
					return new Variable<Name>(p);
				}
			}
		);

	public Parser<String, List<Variable<Name>>> VARIABLES_PARSER = 
		zeroOrMore("VARIABLES_PARSER", VARIABLE_PARSER);

	public Parser<String, List<Variable<Name>>> VARIABLES_REQ_PARSER = 
		oneOrMore("VARIABLES_REQ_PARSER", VARIABLE_PARSER);

	public Parser<String, Number<Name>> ALTERNATIVE_NUM_PARSER =
		thenParser3("ALTERNATIVE_NUM_PARSER", literal("<"), NUMBER_PARSER, literal(">"), 
			new Function3<Number<Name>, String, Number<Name>, String>() {
				public Number<Name> execute(String p1, Number<Name> p2, String p3) {
					return p2;
				}
			}
		);

	public Parser<String, Alternative<Name>> ALTERNATIVE_PARSER =
		thenParser4("ALTERNATIVE_PARSER", ALTERNATIVE_NUM_PARSER, VARIABLES_PARSER, literal("->"), EXPRESSION_PARSER,
			new Function4<Alternative<Name>, Number<Name>, List<Variable<Name>>, String, Expression<Name>>() {
				public Alternative<Name> execute(Number<Name> p1, List<Variable<Name>> p2, String p3, Expression<Name> p4) {
					return new Alternative<Name>(p1, p2, p4);
				}
			}
		);

	public Parser<String, List<Alternative<Name>>> ALTERNATIVES_PARSER = 
		oneOrMoreSep("ALTERNATIVES_PARSER", ALTERNATIVE_PARSER, literal(";"));
	
	public Parser<String, Definition<Name>> DEFINITION_PARSER = 
		thenParser3("DEFINITION_PARSER", VARIABLE_PARSER, literal("="), EXPRESSION_PARSER,
			new Function3<Definition<Name>, Variable<Name>, String, Expression<Name>>() {
				public Definition<Name> execute(Variable<Name> p1, String p2, Expression<Name> p3) {
					return new Definition<Name>(p1, p3);
				}
			}
		);
	
	public Parser<String, List<Definition<Name>>> DEFINITIONS_PARSER = 
		oneOrMoreSep("DEFINITIONS_PARSER", DEFINITION_PARSER, literal(";"));
	
	public Parser<String, Operator<Name>> BIN_OPERATOR_PARSER = 
		thenParser3("BIN_OPERATOR_PARSER", new POp(), ATOMIC_EXPRESSION_PARSER, ATOMIC_EXPRESSION_PARSER, 
			new Function3<Operator<Name>, BinaryOperator, Expression<Name>, Expression<Name>>() {
				public Operator<Name> execute(BinaryOperator p1, Expression<Name> p2, Expression<Name> p3) {
					return new Operator<Name>(p1, p2, p3);
				}
			}
		);
	
	public Parser<String, Let<Name>> LET_PARSER = 
		thenParser4("LET_PARSER", literal("let"), DEFINITIONS_PARSER, literal("in"), EXPRESSION_PARSER, 
			new Function4<Let<Name>, String, List<Definition<Name>>, String, Expression<Name>>() {
				public Let<Name> execute(String p1, List<Definition<Name>> p2, String p3, Expression<Name> p4) {
					return new Let<Name>(false, p2, p4);
				}
			}
		);

	public Parser<String, Let<Name>> LETREC_PARSER =
		thenParser4("LETREC_PARSER", literal("letrec"), DEFINITIONS_PARSER, literal("in"), EXPRESSION_PARSER, 
			new Function4<Let<Name>, String, List<Definition<Name>>, String, Expression<Name>>() {
				public Let<Name> execute(String p1, List<Definition<Name>> p2, String p3, Expression<Name> p4) {
					return new Let<Name>(true, p2, p4);
				}
			}
		);
	
	public Parser<String, Case<Name>> CASE_PARSER =
		thenParser4("CASE_PARSER", literal("case"), EXPRESSION_PARSER, literal("of"), ALTERNATIVES_PARSER, 
			new Function4<Case<Name>, String, Expression<Name>, String, List<Alternative<Name>>>() {
				public Case<Name> execute(String p1, Expression<Name> p2, String p3, List<Alternative<Name>> p4) {
					return new Case<Name>(p2, p4);
				}
			}
		);
	
	public Parser<String, Lambda<Name>> LAMBDA_PARSER =
		thenParser4("LAMBDA_PARSER", literal("\\"), VARIABLES_REQ_PARSER, literal("."), EXPRESSION_PARSER, 
			new Function4<Lambda<Name>, String, List<Variable<Name>>, String, Expression<Name>>() {
				public Lambda<Name> execute(String p1, List<Variable<Name>> p2, String p3, Expression<Name> p4) {
					return new Lambda<Name>(p2, p4);
				}
			}
		);
	
	public Parser<String, Application<Name>> APPLICATION_PARSER = 
		thenParser("APPLICATION_PARSER", EXPRESSION_PARSER_NOAPPLY, ATOMIC_EXPRESSION_PARSER, 
			new Function2<Application<Name>, Expression<Name>, Expression<Name>>() {
				public Application<Name> execute(Expression<Name> p1, Expression<Name> p2) {
					return new Application<Name>(p1, p2);
				}
			}
		);

	public Parser<String, Pair<Number<Name>, Number<Name>>> CONSTRUCTOR_NUM_PARSER =
		thenParser3("CONSTRUCTOR_NUM_PARSER", NUMBER_PARSER, literal(","), NUMBER_PARSER, 
			new Function3<Pair<Number<Name>, Number<Name>>, Number<Name>, String, Number<Name>>() {
				public Pair<Number<Name>, Number<Name>> execute(Number<Name> p1, String p2, Number<Name> p3) {
					return new Pair<Number<Name>, Number<Name>>(p1, p3);
				}
			}
		);
	
	public Parser<String, Constructor<Name>> CONSTRUCTOR_PARSER =
		thenParser4("CONSTRUCTOR_PARSER", literal("Pack"), literal("{"), CONSTRUCTOR_NUM_PARSER, literal("}"),
				new Function4<Constructor<Name>, String, String, Pair<Number<Name>, Number<Name>>, String>() {
					public Constructor<Name> execute(String p1, String p2, Pair<Number<Name>, Number<Name>> p3, String p4) {
						return new Constructor<Name>(p3.first, p3.second);
					}
				}
		);
	
	public Parser<String, Expression<Name>> PARENTHESISED_EXPRESSION_PARSER =
		thenParser3("PARENTHESISED_EXPRESSION_PARSER", literal("("), EXPRESSION_PARSER, literal(")"), 
			new Function3<Expression<Name>, String, Expression<Name>, String>() {
				public Expression<Name> execute(String p1, Expression<Name> p2, String p3) {
					return p2;
				}
			}
		);
	
	public Parser<String, Supercombinator<Name>> SUPERCOMBINATOR_PARSER =
		thenParser4("SUPERCOMBINATOR_PARSER", NAME_PARSER, NAMES_PARSER, literal("="), EXPRESSION_PARSER, 
			new Function4<Supercombinator<Name>, Name, List<Name>, String, Expression<Name>>() {
				public Supercombinator<Name> execute(Name p1, List<Name> p2, String p3, Expression<Name> p4) {
					return new Supercombinator<Name>(p1, p2, p4);
				}
			}
		);

	public Parser<String, List<Supercombinator<Name>>> SUPERCOMBINATORS_PARSER = 
		oneOrMoreSep("SUPERCOMBINATORS_PARSER", SUPERCOMBINATOR_PARSER, literal(";"));

	public Parser<String, Program<Name>> PROGRAM_PARSER = 
		apply("PROGRAM_PARSER", SUPERCOMBINATORS_PARSER, 
			new Function1<Program<Name>, List<Supercombinator<Name>>>() {
				public Program<Name> execute(List<Supercombinator<Name>> p) {
					return new Program<Name>(p);
				}
			}
		);
	
	public SdjParser() {
		Parser<String, Expression<Name>> nalt1 = alternative1("EXPRESSION_PARSER_nalt1", BIN_OPERATOR_PARSER, LET_PARSER);
		Parser<String, Expression<Name>> nalt2 = alternative1("EXPRESSION_PARSER_nalt2", LETREC_PARSER, CASE_PARSER);
		Parser<String, Expression<Name>> nalt3 = alternative1("EXPRESSION_PARSER_nalt3", LAMBDA_PARSER, ATOMIC_EXPRESSION_PARSER);
		EXPRESSION_PARSER_NOAPPLY.delegate = alternative1("EXPRESSION_PARSER_nalt123", alternative1("EXPRESSION_PARSER_nalt12", nalt1, nalt2), nalt3);
		
		Parser<String, Expression<Name>> alt1 = alternative1("EXPRESSION_PARSER_alt1", APPLICATION_PARSER, BIN_OPERATOR_PARSER);
		Parser<String, Expression<Name>> alt2 = alternative1("EXPRESSION_PARSER_alt2", LET_PARSER, LETREC_PARSER);
		Parser<String, Expression<Name>> alt3 = alternative1("EXPRESSION_PARSER_alt3", CASE_PARSER, LAMBDA_PARSER);
		
		Parser<String, Expression<Name>> alt12 = alternative1("EXPRESSION_PARSER_alt12", alt1, alt2);
		Parser<String, Expression<Name>> alt3A = alternative1("EXPRESSION_PARSER_alt3A", alt3, ATOMIC_EXPRESSION_PARSER);
		EXPRESSION_PARSER.delegate = alternative("EXPRESSION_PARSER_alt123A", alt12, alt3A);
		
		// Atomic
		Parser<String, Expression<Name>> atAlt1 = alternative1("ATOMIC_EXPRESSION_PARSER_alt1", VARIABLE_PARSER, NUMBER_PARSER);
		Parser<String, Expression<Name>> atAlt2 = alternative1("ATOMIC_EXPRESSION_PARSER_alt2", CONSTRUCTOR_PARSER, PARENTHESISED_EXPRESSION_PARSER);
		ATOMIC_EXPRESSION_PARSER.delegate = alternative("ATOMIC_EXPRESSION_PARSER", atAlt1, atAlt2);
	}
	
	@Override
	protected Parser<String, Program<Name>> getParser() {
		return PROGRAM_PARSER;
	}
}
