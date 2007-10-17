package org.sodeja.scheme2.execute.form;

import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.sodeja.scheme2.Utils;
import org.sodeja.scheme2.execute.Form;
import org.sodeja.scheme2.execute.Frame;
import org.sodeja.scheme2.model.Combination;
import org.sodeja.scheme2.model.Symbol;
import org.sodeja.scheme2.model.Token;

public class LetForm implements Form {
	@Override
	public Object eval(Frame frame, Deque<Token> parts) {
		if(parts.size() < 2) {
			throw new IllegalArgumentException("Expect at least two expressions - var binding and and executed");
		}
		
		Token token = Utils.head(parts);
		if(! (token instanceof Combination)) {
			throw new IllegalArgumentException("Var bingings part has form ((<var1> <exp1>) (<var1> <exp1>)...)");
		}
		
		Map<Symbol, Object> objects = new HashMap<Symbol, Object>();
		
		Combination bindings = (Combination) token;
		for(Token binding : bindings.tokens) {
			bind(frame, objects, binding);
		}
		
		Frame newFrame = new Frame(frame, objects);
		
		Object result = null;
		for(Token tok : Utils.tail(parts)) {
			result = newFrame.eval(tok);
		}
		return result;
	}

	private void bind(Frame frame, Map<Symbol, Object> objects, Token bindingTok) {
		if(! (bindingTok instanceof Combination)) {
			throw new IllegalArgumentException("Var binging has form (<var> <exp>)");
		}
		
		Combination bindingComb = (Combination) bindingTok;
		if(bindingComb.size() != 2) {
			throw new IllegalArgumentException("Var binging has form (<var> <exp>)");
		}
		
		Iterator<Token> combIte = bindingComb.tokens.iterator();
		
		Token varPart = combIte.next();
		if(! (varPart instanceof Symbol)) {
			throw new IllegalArgumentException("Var binging has form (<var> <exp>)");
		}
		
		// If we construct frame now and use it in every consequent expression maybe letrec?
		objects.put((Symbol) varPart, frame.eval(combIte.next()));
	}
}
