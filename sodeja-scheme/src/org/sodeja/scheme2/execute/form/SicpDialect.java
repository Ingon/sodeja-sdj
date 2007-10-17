package org.sodeja.scheme2.execute.form;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.scheme2.execute.Dialect;
import org.sodeja.scheme2.execute.Form;
import org.sodeja.scheme2.model.Symbol;

public class SicpDialect implements Dialect {
	
	private Map<String, Form> forms;
	
	public SicpDialect() {
		forms = new HashMap<String, Form>() {
			private static final long serialVersionUID = -3409936770843206847L;
		{
			put("def", new DefineForm());
			put("\\", new LambdaForm());
			
			put("if", new IfForm());
			put("cond", new CondForm());
			put("let", new LetForm());
			put("quote", new QuoteForm());
			put("set!", new SetForm());
		}};
	}
	
	@Override
	public Form search(Symbol symbol) {
		return forms.get(symbol.value);
	}
}
