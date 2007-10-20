package org.sodeja.runtime.scheme;

import org.sodeja.runtime.abs.AbstractDialect;
import org.sodeja.runtime.scheme.model.Combination;
import org.sodeja.runtime.scheme.model.Symbol;

public class SchemeDialect extends AbstractDialect<SchemeExpression> {
	@Override
	protected SchemeExpression decompose(SchemeExpression expression) {
		return ((Combination) expression).get(0);
	}

	@Override
	protected boolean isSupported(SchemeExpression expression) {
		return expression instanceof Combination;
	}
	
	protected void introduce(String val, SchemeForm form) {
		mapping.put(new Symbol(val), form);
	}
}
