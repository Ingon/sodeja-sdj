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
	public SILObject copy() {
		SILDefaultObject copy = new SILDefaultObject(type);
		for(int i = 0;i < instanceVariables.length;i++) {
			copy.instanceVariables[i] = instanceVariables[i].copy();
		}
		return copy;
	}

	@Override
	public SILObject get(String reference) {
		int index = type.getInstanceVariableIndex(reference);
		if(index < 0) {
			return null;
		}
		return this.instanceVariables[index];
	}

	@Override
	public void set(String reference, SILObject value) {
		int index = type.getInstanceVariableIndex(reference);
		if(index < 0) {
			throw new UnsupportedOperationException("Global variable set.");
		}
		this.instanceVariables[index] = value;
	}
}
