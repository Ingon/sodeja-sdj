package org.sodeja.scheme2.execute.form;

import java.util.Deque;
import java.util.Iterator;

import org.sodeja.scheme2.execute.Form;
import org.sodeja.scheme2.execute.Frame;
import org.sodeja.scheme2.model.Symbol;
import org.sodeja.scheme2.model.Token;

public class SetForm implements Form {
	@Override
	public Object eval(Frame frame, Deque<Token> parts) {
		if(parts.size() != 2) {
			throw new IllegalArgumentException("Should give a variable and an expression!");
		}
		Iterator<Token> setIte = parts.iterator();
		
		Token varTok = setIte.next();
		if(! (varTok instanceof Symbol)) {
			throw new IllegalArgumentException("Should give a variable and an expression!");
		}
		
		Object value = frame.eval(setIte.next());
		frame.update((Symbol) varTok, value);
		return value;
	}
}
