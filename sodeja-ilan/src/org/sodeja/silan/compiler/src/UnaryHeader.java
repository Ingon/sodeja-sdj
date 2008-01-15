package org.sodeja.silan.compiler.src;

import java.util.Collections;
import java.util.List;

public class UnaryHeader implements MethodHeader {
	public final String selector;

	public UnaryHeader(String selector) {
		this.selector = selector;
	}

	@Override
	public List<String> getArguments() {
		return Collections.emptyList();
	}

	@Override
	public String getSelector() {
		return selector;
	}
}
