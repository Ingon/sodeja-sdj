package org.sodeja.sdj.parser;

import org.sodeja.functional.Predicate1;
import org.sodeja.parsec.combinator.SatisfiesParser;

public class PVar extends SatisfiesParser<String> {
	
	private static final String[] KEYWORDS = {"let", "letrec", "case", "in", "of", "Pack"};
	
	private static boolean isKeyword(String str) {
		for(String keyword : KEYWORDS) {
			if(keyword.equals(str)) {
				return true;
			}
		}
		
		return false;
	}
	
	public PVar(String name) {
		super(name, new Predicate1<String>() {
			public Boolean execute(String p) {
				if(isKeyword(p)) {
					return false;
				}
				
				return Character.isJavaIdentifierStart(p.charAt(0));
			}
		});
	}
}
