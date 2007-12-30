package org.sodeja.ilan.parser;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.ilan.ildk.ILInteger;
import org.sodeja.ilan.ildk.ILNumber;
import org.sodeja.ilan.ildk.ILSymbol;
import org.sodeja.ilan.lexer.CompoundDatum;
import org.sodeja.ilan.lexer.Datum;
import org.sodeja.ilan.lexer.IdentifierDatum;
import org.sodeja.ilan.lexer.LexemeDatum;
import org.sodeja.ilan.lexer.ListDatum;
import org.sodeja.ilan.lexer.NumberDatum;

public class Parser {
	public static List<Expression> parse(List<Datum> datums) {
		List<Expression> result = new ArrayList<Expression>();
		for(Datum datum : datums) {
			result.add(parse(datum));
		}
		return result;
	}

	private static Expression parse(Datum datum) {
		if(datum instanceof LexemeDatum) {
			return parseLexeme((LexemeDatum<?>) datum);
		}
		
		return parseCompound((CompoundDatum) datum);
	}

	private static Expression parseLexeme(LexemeDatum<?> datum) {
		if(datum instanceof IdentifierDatum) {
			return new VariableExpression(makeSymbol(((IdentifierDatum) datum)));
		} else if(datum instanceof NumberDatum) {
			Number num = ((NumberDatum) datum).value;
			ILNumber ilnum = null;
			if(num instanceof Integer) {
				ilnum = new ILInteger((Integer) num);
			} else {
				throw new UnsupportedOperationException();
			}
			
			return new ValueExpression(ilnum);
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
		if(name.value.equals("def")) {
			return parseDef(datum);
		}
		
		return parseApply(datum);
	}

	private static Expression parseDef(ListDatum datum) {
		if(datum.size() > 3) {
			throw new IllegalArgumentException("Wrong def expression");
		}
		if(! (datum.get(1) instanceof IdentifierDatum)) {
			throw new IllegalArgumentException("Wrong def expression");
		}
		
		return new DefExpression(makeSymbol((IdentifierDatum) datum.get(1)), parse(datum.get(2)));
	}

	private static Expression parseApply(ListDatum datum) {
		throw new UnsupportedOperationException();
	}
	
	private static ILSymbol makeSymbol(IdentifierDatum datum) {
		return new ILSymbol(datum.value);
	}
}
