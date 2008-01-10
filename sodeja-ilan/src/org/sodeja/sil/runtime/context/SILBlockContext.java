package org.sodeja.sil.runtime.context;

import org.sodeja.collections.ListUtils;
import org.sodeja.sil.runtime.References;
import org.sodeja.sil.runtime.SILNamedObject;

public class SILBlockContext extends SILNamedObject {
	public SILBlockContext() {
		super(References.SIL_BLOCK_CTX_CLASS, ListUtils.asList(
				References.SIL_BLOCK_CTX_CALLER,
				References.SIL_BLOCK_CTX_INSTRUCTION_POINTER,
				References.SIL_BLOCK_CTX_STACK_POINTER,
				References.SIL_BLOCK_CTX_ARGUMENT_COUNT,
				References.SIL_BLOCK_CTX_INITIAL_INSTRUCTION_POINTER,
				References.SIL_BLOCK_CTX_HOME,
				References.SIL_BLOCK_CTX_STACK));
	}
}
