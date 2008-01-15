package org.sodeja.silan;

public class SILDefaultObject implements SILObject {
	private final SILClass type;
	
	private final SILObject[] instanceVariables;
	
	public SILDefaultObject(SILClass type) {
		this.type = type;
		instanceVariables = new SILObject[type.getInstanceVariablesCount()];
	}

	@Override
	public SILClass getType() {
		return type;
	}

	@Override
	public void set(String reference, SILObject value) {
		int index = type.getInstanceVariableIndex(reference);
		if(index < 0) {
			throw new UnsupportedOperationException("Global variable set.");
		}
		this.instanceVariables[index] = value;
	}

	@Override
	public SILObject get(String reference) {
		int index = type.getInstanceVariableIndex(reference);
		if(index < 0) {
			throw new UnsupportedOperationException("Global variable set.");
		}
		return this.instanceVariables[index];
	}
}
