package org.sodeja.runtime.scheme.form;

import org.sodeja.runtime.scheme.SchemeDialect;
import org.sodeja.runtime.scheme.form.sicp.CondForm;
import org.sodeja.runtime.scheme.form.sicp.DefineForm;
import org.sodeja.runtime.scheme.form.sicp.IfForm;
import org.sodeja.runtime.scheme.form.sicp.LambdaForm;
import org.sodeja.runtime.scheme.form.sicp.LetForm;
import org.sodeja.runtime.scheme.form.sicp.QuoteForm;
import org.sodeja.runtime.scheme.form.sicp.SetForm;

public class SicpDialect extends SchemeDialect {
	@Override
	protected void init() {
		introduce("def", new DefineForm());
		introduce("\\", new LambdaForm());
		
		introduce("if", new IfForm());
		introduce("cond", new CondForm());
		introduce("let", new LetForm());
		introduce("quote", new QuoteForm());
		introduce("set!", new SetForm());
	}
}
