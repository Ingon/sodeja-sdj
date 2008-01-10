package org.sodeja.sil.runtime;

import org.sodeja.collections.ListUtils;

public class SILClass extends SILNamedObject {
	public SILClass(SILClass type) {
		super(type, ListUtils.asList("superclass", "messageDictionary", "instanceSpecification"));
	}
}
