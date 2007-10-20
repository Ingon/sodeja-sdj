package org.sodeja.scheme2.execute.form;

import java.util.Deque;

import org.sodeja.functional.Function1;
import org.sodeja.scheme2.Utils;
import org.sodeja.scheme2.execute.Form;
import org.sodeja.scheme2.execute.Frame;
import org.sodeja.scheme2.model.Combination;
import org.sodeja.scheme2.model.Symbol;
import org.sodeja.scheme2.model.Token;

public class LambdaForm implements Form {
	@Override
	public Object eval(Frame frame, Deque<Token> parts) {
		Combination paramsComb = (Combination) Utils.head(parts);
		Deque<Token> body = Utils.tail(parts);
		return new CompoundProcedure(frame, getParams(paramsComb), body);
	}
	
	private Deque<Symbol> getParams(Combination params) {
		return mapTokenToSymbol(params.tokens);
	}
	
	protected static Deque<Symbol> mapTokenToSymbol(Deque<Token> tokens) {
		return Utils.map(tokens, new Function1<Symbol, Token>() {
			@Override
			public Symbol execute(Token p) {
				return (Symbol) p;
			}});
	}
}
