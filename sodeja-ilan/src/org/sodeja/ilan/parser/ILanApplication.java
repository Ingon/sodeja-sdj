package org.sodeja.ilan.parser;

import java.util.List;

import org.sodeja.ilan.lexer.ILanIdentifier;

public class ILanApplication implements ILanExpression {
	public final List<ILanIdentifier> identifiers;

	public ILanApplication(List<ILanIdentifier> identifiers) {
		this.identifiers = identifiers;
	}
}
