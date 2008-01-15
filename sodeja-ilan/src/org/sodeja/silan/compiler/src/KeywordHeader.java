package org.sodeja.silan.compiler.src;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.lang.StringUtils;

public class KeywordHeader implements MethodHeader {
	public final String selector;
	public final List<KeywordHeaderSegment> segments;
	
	public KeywordHeader(List<KeywordHeaderSegment> segments) {
		this.segments = segments;
		this.selector = StringUtils.appendWithSeparator(segments, "", new Function1<String, KeywordHeaderSegment>() {
			@Override
			public String execute(KeywordHeaderSegment p) {
				return p.keyword;
			}});
	}

	@Override
	public List<String> getArguments() {
		return ListUtils.map(segments, new Function1<String, KeywordHeaderSegment>() {
			@Override
			public String execute(KeywordHeaderSegment p) {
				return p.argument;
			}});
	}

	@Override
	public String getSelector() {
		return selector;
	}
}
