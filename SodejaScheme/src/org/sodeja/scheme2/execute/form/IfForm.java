package org.sodeja.scheme2.execute.form;

import java.util.Deque;
import java.util.Iterator;

import org.sodeja.scheme2.execute.Form;
import org.sodeja.scheme2.execute.Frame;
import org.sodeja.scheme2.model.Token;

public class IfForm implements Form {
	@Override
	public Object eval(Frame frame, Deque<Token> parts) {
		if(parts.size() != 3) {
			throw new IllegalArgumentException("Expecting predicate, true action and false action");
		}
		
		Iterator<Token> evalTokens = parts.iterator();
		
		Token predicate = evalTokens.next();
		Object obj = frame.eval(predicate);
		if(! (obj instanceof Boolean)) {
			throw new IllegalArgumentException("Predicate does not returns boolean!");
		}

		if(((Boolean) obj).booleanValue()) {
			return frame.eval(evalTokens.next());
		} else {
			// Miss the second thing
			evalTokens.next();
			return frame.eval(evalTokens.next());
		}
	}
}
