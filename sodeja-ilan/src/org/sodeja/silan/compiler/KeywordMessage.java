package org.sodeja.silan.compiler;

import java.util.List;

import org.sodeja.functional.Function1;
import org.sodeja.lang.StringUtils;

public class KeywordMessage implements Message {
	public final String selector;
	public final List<KeywordMessageArgument> arguments;
	
	public KeywordMessage(List<KeywordMessageArgument> arguments) {
		this.arguments = arguments;
		this.selector = StringUtils.appendWithSeparator(arguments, "", new Function1<String, KeywordMessageArgument>() {
			@Override
			public String execute(KeywordMessageArgument p) {
				return p.keyword;
			}});
	}
}
