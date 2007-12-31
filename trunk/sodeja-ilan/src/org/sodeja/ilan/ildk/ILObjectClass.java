package org.sodeja.ilan.ildk;

public class ILObjectClass extends ILClass {
	private static final ILObjectClass INSTANCE = new ILObjectClass();
	
	public static ILObjectClass getInstance() {
		return INSTANCE;
	}
	
	private ILObjectClass() {
		super(new ILSymbol("ILObject"), null);
	}
}
