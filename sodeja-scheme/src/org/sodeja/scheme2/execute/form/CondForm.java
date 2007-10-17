package org.sodeja.scheme2.execute.form;

import java.util.Deque;
import java.util.Iterator;

import org.sodeja.scheme2.execute.Form;
import org.sodeja.scheme2.execute.Frame;
import org.sodeja.scheme2.model.Combination;
import org.sodeja.scheme2.model.Symbol;
import org.sodeja.scheme2.model.Token;

public class CondForm implements Form {
	@Override
	public Object eval(Frame frame, Deque<Token> parts) {
		for(Token clauseTok : parts) {
			if(! (clauseTok instanceof Combination)) {
				throw new IllegalArgumentException("Every clause has (<predicate> <action>) form!");
			}
			
			Combination clause = (Combination) clauseTok;
			if(clause.size() != 2) {
				throw new IllegalArgumentException("Every clause has (<predicate> <action>) form!");
			}
			
			Iterator<Token> clauseIte = clause.tokens.iterator();
			Token predicate = clauseIte.next();
			Boolean evalResult = evalToken(frame, predicate);
			if(! evalResult.booleanValue()) {
				continue;
			}
			
			Object result = null;
			for(;clauseIte.hasNext();) {
				result = frame.eval(clauseIte.next());
			}
			return result;
		}
		
		throw new RuntimeException("No clause matches!");
	}
	
	private Boolean evalToken(Frame frame, Token predicate) {
		if(predicate instanceof Symbol && ((Symbol) predicate).value.equals("else")) {
			return Boolean.TRUE;
		}
		
		Object obj = frame.eval(predicate);
		if(! (obj instanceof Boolean)) {
			throw new IllegalArgumentException("Predicate does not returns boolean!");
		}

		return (Boolean) obj;
	}
}
