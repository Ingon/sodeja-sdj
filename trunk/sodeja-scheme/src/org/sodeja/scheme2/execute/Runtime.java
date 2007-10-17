package org.sodeja.scheme2.execute;

import java.util.Deque;
import java.util.Map;

import org.sodeja.math.Rational;
import org.sodeja.scheme2.Utils;
import org.sodeja.scheme2.execute.form.SicpDialect;
import org.sodeja.scheme2.execute.procedure.AritmeticLibrary;
import org.sodeja.scheme2.model.Combination;
import org.sodeja.scheme2.model.Symbol;
import org.sodeja.scheme2.model.Token;

public class Runtime {
	
	private final Dialect dialect;
	private final Frame rootFrame;
	
	public Runtime() {
		this(new SicpDialect());
	}
	
	public Runtime(Dialect dialect) {
		this.dialect = dialect;
		rootFrame = new Frame(this);
		loadLibraries();
	}
	
	private void loadLibraries() {
		loadLibrary(new AritmeticLibrary());
	}
	
	private void loadLibrary(Library library) {
		for(Map.Entry<String, Object> element : library.contents().entrySet()) {
			rootFrame.extend(new Symbol(element.getKey()), element.getValue());
		}
	}
	
	public Object evalToken(Token token) {
		return eval(rootFrame, token);
	}

	protected Object eval(Frame frame, Token token) {
		if(token instanceof Symbol) {
			return evalSymbol(frame, (Symbol) token);
		}
		
		if(token instanceof Combination) {
			return evalCombination(frame, (Combination) token);
		}
		
		return Utils.unknownToken(token);
	}
	
	private Object evalSymbol(Frame frame, Symbol symbol) {
		try {
			return new Rational(symbol.value);
		} catch(NumberFormatException exc) {
			return frame.getSymbolValue(symbol);
		}
	}

	private Object evalCombination(Frame frame, Combination comb) {
		Token token = comb.head();
		if(token instanceof Symbol) {
			return evalCombinationSymbol(frame, (Symbol) token, comb.tail());
		}
		
		if(token instanceof Combination) {
			return evalApplication(frame, (Combination) token, comb.tail());
		}
		
		return Utils.unknownToken(token);
	}
	
	private Object evalCombinationSymbol(Frame frame, Symbol symbol, Deque<Token> parts) {
		Form form = dialect.search(symbol);
		if(form != null) {
			return form.eval(frame, parts);
		}
		
		Object val = frame.getSymbolValue(symbol);
		return apply(frame, val, parts);
	}
	
	private Object evalApplication(Frame frame, Combination token, Deque<Token> parts) {
		Object val = eval(frame, token);
		return apply(frame, val, parts);
	}

	private Object apply(Frame frame, Object val, Deque<Token> parts) {
		testForProcedure(val);
		return applyDelegate(frame, (Procedure) val, parts);
	}
	
	private Object applyDelegate(Frame frame, Procedure procedure, Deque<Token> parts) {
		Deque<Object> results = Utils.evalDeque(frame, parts);
		return procedure.apply(results.toArray(new Object[results.size()]));
	}
	
	private void testForProcedure(Object val) {
		if(! (val instanceof Procedure)) {
			throw new IllegalArgumentException("Not a procedure!");
		}
	}
}
