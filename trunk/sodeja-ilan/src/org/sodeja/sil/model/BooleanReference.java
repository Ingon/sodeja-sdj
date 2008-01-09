package org.sodeja.sil.model;

public class BooleanReference {
	private static final BooleanReference instanceTrue = new BooleanReference(Boolean.TRUE);
	private static final BooleanReference instanceFalse = new BooleanReference(Boolean.FALSE);
	
	public static BooleanReference getTrueInstance() {
		return instanceTrue;
	}

	public static BooleanReference getFalseInstance() {
		return instanceFalse;
	}
	
	public final Boolean value;
	
	private BooleanReference(Boolean value) {
		this.value = value;
	}
}
