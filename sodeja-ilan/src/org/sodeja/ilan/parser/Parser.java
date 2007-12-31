package org.sodeja.ilan.parser;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.ilan.ildk.ILBoolean;
import org.sodeja.ilan.ildk.ILCharacter;
import org.sodeja.ilan.ildk.ILInteger;
import org.sodeja.ilan.ildk.ILNumber;
import org.sodeja.ilan.ildk.ILReal;
import org.sodeja.ilan.ildk.ILString;
import org.sodeja.ilan.ildk.ILSymbol;
import org.sodeja.ilan.lexer.BooleanDatum;
import org.sodeja.ilan.lexer.CharacterDatum;
import org.sodeja.ilan.lexer.CompoundDatum;
import org.sodeja.ilan.lexer.Datum;
import org.sodeja.ilan.lexer.IdentifierDatum;
import org.sodeja.ilan.lexer.LexemeDatum;
import org.sodeja.ilan.lexer.ListDatum;
import org.sodeja.ilan.lexer.NumberDatum;
import org.sodeja.ilan.lexer.StringDatum;

public class Parser {
	public static List<Expression> parse(List<Datum> datums) {
		return ListUtils.map(datums, new Function1<Expression, Datum>() {
			@Override
			public Expression execute(Datum p) {
				return parseDatum(p);
			}});
	}

	private static Expression parseDatum(Datum datum) {
		if(datum instanceof LexemeDatum) {
			return parseLexeme((LexemeDatum<?>) datum);
		}
		
		return parseCompound((CompoundDatum) datum);
	}

	private static Expression parseLexeme(LexemeDatum<?> datum) {
		if(datum instanceof IdentifierDatum) {
			return new VariableExpression(getSymbol(datum));
		} else if(datum instanceof NumberDatum) {
			Number num = (Number) datum.value;
			ILNumber ilnum = null;
			if(num instanceof Long) {
				ilnum = new ILInteger((Long) num);
			} else if(num instanceof Double) {
				ilnum = new ILReal((Double) num);
			} else {
				throw new UnsupportedOperationException();
			}
			
			return new ValueExpression(ilnum);
		} else if(datum instanceof BooleanDatum) {
			Boolean value = (Boolean) datum.value;
			return new ValueExpression(new ILBoolean(value));
		} else if(datum instanceof CharacterDatum) {
			Character value = (Character) datum.value;
			return new ValueExpression(new ILCharacter(value));
		} else if(datum instanceof StringDatum) {
			String value = (String) datum.value;
			return new ValueExpression(new ILString(value));
		}
		
		throw new UnsupportedOperationException();
	}

	private static Expression parseCompound(CompoundDatum datum) {
		if(datum instanceof ListDatum) {
			return parseList((ListDatum) datum);
		}
		
		throw new UnsupportedOperationException();
	}

	private static Expression parseList(ListDatum datum) {
		Datum data = datum.get(0);
		if(data instanceof CompoundDatum) {
			return parseApply(datum);
		}
		
		if(data instanceof IdentifierDatum) {
			return parseForm(datum);
		}
		
		throw new UnsupportedOperationException();
	}

	private static Expression parseForm(ListDatum datum) {
		IdentifierDatum name = (IdentifierDatum) datum.get(0);
		String value = name.value;
		if(value.equals("def")) {
			return parseDef(datum);
		} else if(value.equals("\\")) {
			return parseLambda(datum);
		} else if(value.equals("defun")) {
			return parseDefun(datum);
		} else if(value.equals("if")) {
			return parseIf(datum);
		}
		
		return parseApply(datum);
	}

	private static Expression parseDef(ListDatum datum) {
		if(datum.size() > 3) {
			throw new RuntimeException("Wrong def expression");
		}
		if(! (datum.get(1) instanceof IdentifierDatum)) {
			throw new RuntimeException("Wrong def expression");
		}
		
		return new DefExpression(getSymbol(datum.get(1)), parseDatum(datum.get(2)));
	}

	private static Expression parseLambda(ListDatum datum) {
		if(datum.size() < 3) {
			throw new RuntimeException("Wrong lambda expression");
		}
		
		if(! (datum.get(1) instanceof ListDatum)) {
			throw new RuntimeException("Wrong lambda expression - should have parameters list");
		}
		
		List<ILSymbol> params = ListUtils.map((ListDatum) datum.get(1), new Function1<ILSymbol, Datum>() {
			@Override
			public ILSymbol execute(Datum p) {
				if(! (p instanceof IdentifierDatum)) {
					throw new RuntimeException("Wrong lambda expression - parameters are olny identifiers");
				}
				
				return getSymbol(p);
			}});
		
		List<Datum> bodyDatum = datum.subList(2, datum.size());
		List<Expression> body = parse(bodyDatum);
		
		return new LambdaExpression(params, body);
	}
	
	private static Expression parseDefun(ListDatum datum) {
		if(datum.size() < 4) {
			throw new RuntimeException("Wrong defun expression");
		}
		
		if(! (datum.get(1) instanceof IdentifierDatum)) {
			throw new RuntimeException("Wrong def expression");
		}
		
		ListDatum rest = new ListDatum();
		rest.addAll(datum);
		rest.remove(0);
		
		return new DefExpression(getSymbol(datum.get(1)), parseLambda(rest));
	}

	private static Expression parseIf(ListDatum datum) {
		if(datum.size() != 4) {
			throw new RuntimeException("Wrong if clause");
		}
		
		List<Expression> subexpr = parse(ListUtils.tail(datum));
		return new IfExpression(subexpr.get(0), subexpr.get(1), subexpr.get(2));
	}
	
	private static Expression parseApply(ListDatum datum) {
		return new ApplyExpression(parse(datum));
	}
	
	private static ILSymbol getSymbol(Datum datum) {
		return makeSymbol((IdentifierDatum) datum);
	}
	
	private static ILSymbol makeSymbol(IdentifierDatum datum) {
		return new ILSymbol(datum.value);
	}
}
