package org.sodeja.sil.runtime;

import java.util.List;

public class SILDefaultObjectContents implements SILObjectContents {
	public final List<Reference> references;

	public SILDefaultObjectContents(List<Reference> references) {
		this.references = references;
	}
}
