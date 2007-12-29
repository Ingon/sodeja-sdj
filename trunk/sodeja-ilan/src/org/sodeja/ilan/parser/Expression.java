package org.sodeja.ilan.parser;

import org.sodeja.ilan.runtime.ILFrame;

public interface Expression {
	Object eval(ILFrame frame);
}
