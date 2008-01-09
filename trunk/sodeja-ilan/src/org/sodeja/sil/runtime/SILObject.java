package org.sodeja.sil.runtime;

import java.util.List;

public class SILObject {
	public final Reference silClass;
	public final List<Reference> contents;
	
	public SILObject(Reference silClass, List<Reference> contents) {
		this.silClass = silClass;
		this.contents = contents;
	}
}
