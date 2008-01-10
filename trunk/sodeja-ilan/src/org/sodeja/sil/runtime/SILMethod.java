package org.sodeja.sil.runtime;

import org.sodeja.collections.ListUtils;

public class SILMethod extends SILNamedObject {
	public SILMethod(Reference type, Reference literals, Reference body) {
		super(type, ListUtils.asList(References.SIL_METHOD_LITERALS, References.SIL_METHOD_BODY));
		at_put(References.SIL_METHOD_LITERALS, literals);
		at_put(References.SIL_METHOD_BODY, body);
	}
}
