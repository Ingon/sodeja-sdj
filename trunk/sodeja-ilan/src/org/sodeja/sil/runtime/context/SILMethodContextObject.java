package org.sodeja.sil.runtime.context;

import org.sodeja.collections.ListUtils;
import org.sodeja.sil.runtime.References;
import org.sodeja.sil.runtime.SILNamedObject;

public class SILMethodContextObject extends SILNamedObject {
	public SILMethodContextObject() {
		super(References.SIL_METHOD_CTX_CLASS, ListUtils.asList(
				References.SIL_METHOD_CTX_SENDER,
				References.SIL_METHOD_CTX_INSTRUCTION_POINTER,
				References.SIL_METHOD_CTX_STACK_POINTER,
				References.SIL_METHOD_CTX_METHOD,
				References.SIL_METHOD_CTX_RECEIVER,
				References.SIL_METHOD_CTX_ARGUMENTS,
				References.SIL_METHOD_CTX_TEMPORARIES,
				References.SIL_METHOD_CTX_STACK));
	}
}
