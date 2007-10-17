package org.sodeja.scheme2.execute.form;

import java.util.Deque;

import org.sodeja.scheme2.Utils;
import org.sodeja.scheme2.execute.Form;
import org.sodeja.scheme2.execute.Frame;
import org.sodeja.scheme2.execute.Procedure;
import org.sodeja.scheme2.model.Combination;
import org.sodeja.scheme2.model.Symbol;
import org.sodeja.scheme2.model.Token;

public class DefineForm implements Form {
	@Override
	public Object eval(Frame frame, Deque<Token> parts) {
		Token token = parts.peekFirst();
		if(token instanceof Symbol) {
			return defineSymbol(frame, (Symbol) token, Utils.tail(parts));
		} 

		if(token instanceof Combination) {
			return defineLambda(frame, (Combination) token, Utils.tail(parts));
		}
		
		return Utils.unknownToken(token);
	}

	private Object defineSymbol(Frame frame, Symbol symbol, Deque<Token> body) {
		Token valueToken = Utils.head(body);
		Object value = frame.eval(valueToken);
		frame.extend(symbol, value);
		return symbol;
	}

	private Object defineLambda(Frame frame, Combination token, Deque<Token> body) {
		Symbol symbol = (Symbol) token.head();
		Deque<Token> paramsTok = token.tail();
		Procedure proc = new CompoundProcedure(frame, LambdaForm.mapTokenToSymbol(paramsTok), body);
		frame.extend(symbol, proc);
		return symbol;
	}
}
