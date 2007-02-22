package org.sodeja.sdj.parser;

import java.util.List;

import org.sodeja.functional.Function1;
import org.sodeja.functional.Function2;
import org.sodeja.functional.Function3;
import org.sodeja.functional.Function4;
import org.sodeja.functional.Pair;
import org.sodeja.parsec.ParsecUtils;
import org.sodeja.parsec.Parser;
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

public class SdjParser {
	
	public DelegateParser<String, Expression<Name>> EXPRESSION_PARSER = new DelegateParser<String, Expression<Name>>("EXPRESSION_PARSER");
	
	public DelegateParser<String, Expression<Name>> EXPRESSION_PARSER_NOAPPLY = new DelegateParser<String, Expression<Name>>("EXPRESSION_PARSER");
	
	public DelegateParser<String, Expression<Name>> ATOMIC_EXPRESSION_PARSER = new DelegateParser<String, Expression<Name>>("ATOMIC_EXPRESSION_PARSER");
	
	public Parser<String, Name> NAME_PARSER = 
		ParsecUtils.apply("NAME_PARSER_apply", new PVar("NAME_PARSER"), 
			new Function1<Name, String>() {
				public Name execute(String p) {
					return new Name(p);
				}
			}
		);
	
	public Parser<String, List<Name>> NAMES_PARSER = ParsecUtils.zeroOrMore("NAMES_PARSER", NAME_PARSER);
	
	public Parser<String, Number<Name>> NUMBER_PARSER = 
		ParsecUtils.apply("NUMBER_PARSER_apply", new PNum("NUMBER_PARSER"), 
			new Function1<Number<Name>, Integer> () {
				public Number<Name> execute(Integer p) {
					return new Number<Name>(p);
				}
			}
		);
	
	public Parser<String, Variable<Name>> VARIABLE_PARSER = 
		ParsecUtils.apply("VARIABLE_PARSER", NAME_PARSER,
			new Function1<Variable<Name>, Name>() {
				public Variable<Name> execute(Name p) {
					return new Variable<Name>(p);
				}
			}
		);

	public Parser<String, List<Variable<Name>>> VARIABLES_PARSER = ParsecUtils.zeroOrMore("VARIABLES_PARSER", VARIABLE_PARSER);

	public Parser<String, List<Variable<Name>>> VARIABLES_REQ_PARSER = ParsecUtils.oneOrMore("VARIABLES_REQ_PARSER", VARIABLE_PARSER);

	public Parser<String, Number<Name>> ALTERNATIVE_NUM_PARSER =
		ParsecUtils.thenParser3("ALTERNATIVE_NUM_PARSER", new PLit("<"), NUMBER_PARSER, new PLit(">"), 
			new Function3<Number<Name>, String, Number<Name>, String>() {
				public Number<Name> execute(String p1, Number<Name> p2, String p3) {
					return p2;
				}
			}
		);

	public Parser<String, Alternative<Name>> ALTERNATIVE_PARSER =
		ParsecUtils.thenParser4("ALTERNATIVE_PARSER", ALTERNATIVE_NUM_PARSER, VARIABLES_PARSER, new PLit("->"), EXPRESSION_PARSER,
			new Function4<Alternative<Name>, Number<Name>, List<Variable<Name>>, String, Expression<Name>>() {
				public Alternative<Name> execute(Number<Name> p1, List<Variable<Name>> p2, String p3, Expression<Name> p4) {
					return new Alternative<Name>(p1, p2, p4);
				}
			}
		);

	public Parser<String, List<Alternative<Name>>> ALTERNATIVES_PARSER = ParsecUtils.oneOrMoreSep("ALTERNATIVES_PARSER", ALTERNATIVE_PARSER, new PLit(";"));
	
	public Parser<String, Definition<Name>> DEFINITION_PARSER = 
		ParsecUtils.thenParser3("DEFINITION_PARSER", VARIABLE_PARSER, new PLit("="), EXPRESSION_PARSER,
			new Function3<Definition<Name>, Variable<Name>, String, Expression<Name>>() {
				public Definition<Name> execute(Variable<Name> p1, String p2, Expression<Name> p3) {
					return new Definition<Name>(p1, p3);
				}
			}
		);
	
	public Parser<String, List<Definition<Name>>> DEFINITIONS_PARSER = ParsecUtils.oneOrMoreSep("DEFINITIONS_PARSER", DEFINITION_PARSER, new PLit(";"));
	
	public Parser<String, Operator<Name>> BIN_OPERATOR_PARSER = 
		ParsecUtils.thenParser3("BIN_OPERATOR_PARSER", new POp(), ATOMIC_EXPRESSION_PARSER, ATOMIC_EXPRESSION_PARSER, 
			new Function3<Operator<Name>, BinaryOperator, Expression<Name>, Expression<Name>>() {
				public Operator<Name> execute(BinaryOperator p1, Expression<Name> p2, Expression<Name> p3) {
					return new Operator<Name>(p1, p2, p3);
				}
			}
		);
	
	public Parser<String, Let<Name>> LET_PARSER = 
		ParsecUtils.thenParser4("LET_PARSER", new PLit("let"), DEFINITIONS_PARSER, new PLit("in"), EXPRESSION_PARSER, 
			new Function4<Let<Name>, String, List<Definition<Name>>, String, Expression<Name>>() {
				public Let<Name> execute(String p1, List<Definition<Name>> p2, String p3, Expression<Name> p4) {
					return new Let<Name>(false, p2, p4);
				}
			}
		);

	public Parser<String, Let<Name>> LETREC_PARSER =
		ParsecUtils.thenParser4("LETREC_PARSER", new PLit("letrec"), DEFINITIONS_PARSER, new PLit("in"), EXPRESSION_PARSER, 
			new Function4<Let<Name>, String, List<Definition<Name>>, String, Expression<Name>>() {
				public Let<Name> execute(String p1, List<Definition<Name>> p2, String p3, Expression<Name> p4) {
					return new Let<Name>(true, p2, p4);
				}
			}
		);
	
	public Parser<String, Case<Name>> CASE_PARSER =
		ParsecUtils.thenParser4("CASE_PARSER", new PLit("case"), EXPRESSION_PARSER, new PLit("of"), ALTERNATIVES_PARSER, 
			new Function4<Case<Name>, String, Expression<Name>, String, List<Alternative<Name>>>() {
				public Case<Name> execute(String p1, Expression<Name> p2, String p3, List<Alternative<Name>> p4) {
					return new Case<Name>(p2, p4);
				}
			}
		);
	
	public Parser<String, Lambda<Name>> LAMBDA_PARSER =
		ParsecUtils.thenParser4("LAMBDA_PARSER", new PLit("\\"), VARIABLES_REQ_PARSER, new PLit("."), EXPRESSION_PARSER, 
			new Function4<Lambda<Name>, String, List<Variable<Name>>, String, Expression<Name>>() {
				public Lambda<Name> execute(String p1, List<Variable<Name>> p2, String p3, Expression<Name> p4) {
					return new Lambda<Name>(p2, p4);
				}
			}
		);
	
	public Parser<String, Application<Name>> APPLICATION_PARSER = 
		ParsecUtils.thenParser("APPLICATION_PARSER", EXPRESSION_PARSER_NOAPPLY, ATOMIC_EXPRESSION_PARSER, 
			new Function2<Application<Name>, Expression<Name>, Expression<Name>>() {
				public Application<Name> execute(Expression<Name> p1, Expression<Name> p2) {
					return new Application<Name>(p1, p2);
				}
			}
		);

	public Parser<String, Pair<Number<Name>, Number<Name>>> CONSTRUCTOR_NUM_PARSER =
		ParsecUtils.thenParser3("CONSTRUCTOR_NUM_PARSER", NUMBER_PARSER, new PLit(","), NUMBER_PARSER, 
			new Function3<Pair<Number<Name>, Number<Name>>, Number<Name>, String, Number<Name>>() {
				public Pair<Number<Name>, Number<Name>> execute(Number<Name> p1, String p2, Number<Name> p3) {
					return new Pair<Number<Name>, Number<Name>>(p1, p3);
				}
			}
		);
	
	public Parser<String, Constructor<Name>> CONSTRUCTOR_PARSER =
		ParsecUtils.thenParser4("CONSTRUCTOR_PARSER", new PLit("Pack"), new PLit("{"), CONSTRUCTOR_NUM_PARSER, new PLit("}"),
				new Function4<Constructor<Name>, String, String, Pair<Number<Name>, Number<Name>>, String>() {
					public Constructor<Name> execute(String p1, String p2, Pair<Number<Name>, Number<Name>> p3, String p4) {
						return new Constructor<Name>(p3.first, p3.second);
					}
				}
		);
	
	public Parser<String, Expression<Name>> PARENTHESISED_EXPRESSION_PARSER =
		ParsecUtils.thenParser3("PARENTHESISED_EXPRESSION_PARSER", new PLit("("), EXPRESSION_PARSER, new PLit(")"), 
			new Function3<Expression<Name>, String, Expression<Name>, String>() {
				public Expression<Name> execute(String p1, Expression<Name> p2, String p3) {
					return p2;
				}
			}
		);
	
	public Parser<String, Supercombinator<Name>> SUPERCOMBINATOR_PARSER =
		ParsecUtils.thenParser4("SUPERCOMBINATOR_PARSER", NAME_PARSER, NAMES_PARSER, new PLit("="), EXPRESSION_PARSER, 
			new Function4<Supercombinator<Name>, Name, List<Name>, String, Expression<Name>>() {
				public Supercombinator<Name> execute(Name p1, List<Name> p2, String p3, Expression<Name> p4) {
					return new Supercombinator<Name>(p1, p2, p4);
				}
			}
		);

	public Parser<String, List<Supercombinator<Name>>> SUPERCOMBINATORS_PARSER = ParsecUtils.oneOrMoreSep("SUPERCOMBINATORS_PARSER", SUPERCOMBINATOR_PARSER, new PLit(";"));

	public Parser<String, Program<Name>> PROGRAM_PARSER = 
		ParsecUtils.apply("PROGRAM_PARSER", SUPERCOMBINATORS_PARSER, 
			new Function1<Program<Name>, List<Supercombinator<Name>>>() {
				public Program<Name> execute(List<Supercombinator<Name>> p) {
					return new Program<Name>(p);
				}
			}
		);
	
	public SdjParser() {
		Parser<String, Expression<Name>> nalt1 = ParsecUtils.alternative1("EXPRESSION_PARSER_nalt1", BIN_OPERATOR_PARSER, LET_PARSER);
		Parser<String, Expression<Name>> nalt2 = ParsecUtils.alternative1("EXPRESSION_PARSER_nalt2", LETREC_PARSER, CASE_PARSER);
		Parser<String, Expression<Name>> nalt3 = ParsecUtils.alternative1("EXPRESSION_PARSER_nalt3", LAMBDA_PARSER, ATOMIC_EXPRESSION_PARSER);
		EXPRESSION_PARSER_NOAPPLY.delegate = ParsecUtils.alternative1("EXPRESSION_PARSER_nalt123", ParsecUtils.alternative1("EXPRESSION_PARSER_nalt12", nalt1, nalt2), nalt3);
		
		Parser<String, Expression<Name>> alt1 = ParsecUtils.alternative1("EXPRESSION_PARSER_alt1", APPLICATION_PARSER, BIN_OPERATOR_PARSER);
		Parser<String, Expression<Name>> alt2 = ParsecUtils.alternative1("EXPRESSION_PARSER_alt2", LET_PARSER, LETREC_PARSER);
		Parser<String, Expression<Name>> alt3 = ParsecUtils.alternative1("EXPRESSION_PARSER_alt3", CASE_PARSER, LAMBDA_PARSER);
		
		Parser<String, Expression<Name>> alt12 = ParsecUtils.alternative1("EXPRESSION_PARSER_alt12", alt1, alt2);
		Parser<String, Expression<Name>> alt3A = ParsecUtils.alternative1("EXPRESSION_PARSER_alt3A", alt3, ATOMIC_EXPRESSION_PARSER);
		EXPRESSION_PARSER.delegate = ParsecUtils.alternative("EXPRESSION_PARSER_alt123A", alt12, alt3A);
		
		// Atomic
		Parser<String, Expression<Name>> atAlt1 = ParsecUtils.alternative1("ATOMIC_EXPRESSION_PARSER_alt1", VARIABLE_PARSER, NUMBER_PARSER);
		Parser<String, Expression<Name>> atAlt2 = ParsecUtils.alternative1("ATOMIC_EXPRESSION_PARSER_alt2", CONSTRUCTOR_PARSER, PARENTHESISED_EXPRESSION_PARSER);
		ATOMIC_EXPRESSION_PARSER.delegate = ParsecUtils.alternative("ATOMIC_EXPRESSION_PARSER", atAlt1, atAlt2);
	}
	
	public Program<Name> parse(List<String> tokens) {
		List<Pair<Program<Name>, List<String>>> result = PROGRAM_PARSER.execute(tokens);
		for(Pair<Program<Name>, List<String>> pair : result) {
			if(pair.second.isEmpty()) {
				return pair.first;
			}
		}
		
		throw new RuntimeException("Syntax error!");
	}
}
