package org.sodeja.sil.runtime;

public class SILObject {
	public final Reference type;
	public final SILObjectContents contents;
	
	public SILObject(Reference type, SILObjectContents contents) {
		this.type = type;
		this.contents = contents;
	}
}
