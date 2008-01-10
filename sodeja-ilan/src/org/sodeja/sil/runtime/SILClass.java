package org.sodeja.sil.runtime;

import org.sodeja.collections.ListUtils;

public class SILClass extends SILNamedObject {
	public SILClass(Reference type) {
		super(type, ListUtils.asList(
				References.SIL_CLASS_SUPERCLASS,
				References.SIL_CLASS_MESSAGE_DICT,
				References.SIL_CLASS_INSTANCE_SPEC));
	}
}
