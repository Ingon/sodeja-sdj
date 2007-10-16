package org.sodeja.scheme.parse;

import static org.sodeja.parsec.ParsecUtils.alternative1;
import static org.sodeja.parsec.ParsecUtils.applyCons;
import static org.sodeja.parsec.ParsecUtils.thenParser3Cons2;
import static org.sodeja.parsec.ParsecUtils.thenParserCons2;
import static org.sodeja.parsec.ParsecUtils.zeroOrMoreSep;
import static org.sodeja.parsec.standart.StandartParsers.justText;
import static org.sodeja.parsec.standart.StandartParsers.literal;
import static org.sodeja.parsec.standart.StandartParsers.rational;

import java.util.List;

import org.sodeja.math.Rational;
import org.sodeja.parsec.DelegateParser;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.semantic.AbstractSemanticParser;
import org.sodeja.scheme.parse.model.Expression;
import org.sodeja.scheme.parse.model.QuoteExpression;
import org.sodeja.scheme.parse.model.RationalExpression;
import org.sodeja.scheme.parse.model.SExpression;
import org.sodeja.scheme.parse.model.Script;
import org.sodeja.scheme.parse.model.SimpleExpression;
import org.sodeja.scheme.parse.model.SymbolExpression;

public class SemanticParser extends AbstractSemanticParser<String, Script>{

	private Parser<String, String> SYMBOL = justText("SYMBOL");

	private Parser<String, Rational> RATIONAL = rational("RATIONAL");
	
	private Parser<String, SymbolExpression> SYMBOL_EXPRESSION = applyCons("SYMBOL_EXPRESSION", SYMBOL, SymbolExpression.class);
	
	private Parser<String, RationalExpression> RATIONAL_EXPRESSION = applyCons("RATIONAL_EXPRESSION", RATIONAL, RationalExpression.class);
	
	private Parser<String, QuoteExpression> QUOTE_EXPRESSION = thenParserCons2("QUOTE_EXPRESSION", literal("'"), justText("QUOTE_EXPRESSION_TEXT"), QuoteExpression.class);
	
	private Parser<String, SimpleExpression<?>> TEXT_EXPRESSION = alternative1("TEXT_EXPRESSION", QUOTE_EXPRESSION, SYMBOL_EXPRESSION);
	
	private Parser<String, SimpleExpression<?>> SIMPLE_EXPRESSION = alternative1("SIMPLE_EXPRESSION", RATIONAL_EXPRESSION, TEXT_EXPRESSION);
	
	private DelegateParser<String, Expression> EXPRESSION = new DelegateParser<String, Expression>("EXPRESSION");
	
	private Parser<String, List<Expression>> EXPRESSIONS = zeroOrMoreSep("EXPRESSIONS", EXPRESSION, literal(" "));

	private Parser<String, SExpression> SEXPRESSION = thenParser3Cons2("SEXPRESSION", literal("("), EXPRESSIONS, literal(")"), SExpression.class);
		
	private Parser<String, Script> SCRIPT = applyCons("SCRIPT", EXPRESSIONS, Script.class);
		
	public SemanticParser() {
		EXPRESSION.delegate = alternative1("EXPRESSION_DELEGATE", SEXPRESSION, SIMPLE_EXPRESSION);
	}
	
	@Override
	protected Parser<String, Script> getParser() {
		return SCRIPT;
	}
}
