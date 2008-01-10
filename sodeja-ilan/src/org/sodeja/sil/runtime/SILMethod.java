package org.sodeja.sil.runtime;

import org.sodeja.collections.ListUtils;

public class SILMethod extends SILNamedObject {
	private static final String BODY = "body";
	private static final String LITERALS = "literals";

	public SILMethod(SILClass type, SILObject literals, SILObject body) {
		super(type, ListUtils.asList(LITERALS, BODY));
		
	}
	
	public SILObject getLiterals() {
		return at(LITERALS);
	}
	
	public SILObject getBody() {
		return at(BODY);
	}
}
