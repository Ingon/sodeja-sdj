package org.sodeja.sdj.parser;

import java.util.List;

import org.sodeja.functional.Pair;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.sdj.expression.BinaryOperator;

public class POp extends AbstractParser<String, BinaryOperator> {
	public POp() {
		super("BIN_OPERATOR");
	}

	@Override
	protected List<Pair<BinaryOperator, List<String>>> executeDelegate(List<String> tokens) {
		for(BinaryOperator op : BinaryOperator.values()) {
			if(op.text.equals(tokens.get(0))) {
				return createWithRemove(op, tokens);
			}
		}
		
		return EMPTY;
	}
}
