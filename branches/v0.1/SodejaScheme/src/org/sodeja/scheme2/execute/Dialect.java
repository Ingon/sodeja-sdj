package org.sodeja.scheme2.execute;

import org.sodeja.scheme2.model.Symbol;

public interface Dialect {
	public Form search(Symbol symbol);
}
