package org.sodeja.sdj.parser;

import org.sodeja.collections.ConsList;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ParseError;
import org.sodeja.parsec.ParseSuccess;
import org.sodeja.parsec.ParsingResult;
import org.sodeja.sdj.expression.BinaryOperator;

public class POp extends AbstractParser<String, BinaryOperator> {
	public POp() {
		super("BIN_OPERATOR");
	}

	@Override
	protected ParsingResult<String, BinaryOperator> executeDelegate(ConsList<String> tokens) {
		for(BinaryOperator op : BinaryOperator.values()) {
			if(op.text.equals(tokens.get(0))) {
				return new ParseSuccess<String, BinaryOperator>(op, tokens.tail());
			}
		}
		
		return new ParseError<String, BinaryOperator>("Expecting binary operator");
	}
}
