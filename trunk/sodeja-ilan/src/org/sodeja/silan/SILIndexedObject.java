package org.sodeja.silan;

public class SILIndexedObject implements SILObject {
	private final SILClass type;
	private final SILObject[] values;
	
	public SILIndexedObject(SILClass type, int size) {
		this.type = type;
		this.values = new SILObject[size];
	}

	public SILIndexedObject(SILClass type, SILObject[] values) {
		this.type = type;
		this.values = values;
	}
	
	@Override
	public SILClass getType() {
		return type;
	}

	@Override
	public SILObject copy() {
		SILIndexedObject copy = new SILIndexedObject(type, values.length);
		for(int i = 0;i < values.length;i++) {
			copy.values[i] = values[i].copy();
		}
		return copy;
	}

	@Override
	public SILObject get(String reference) {
		// TODO so nasty, so wrong
		int index = Integer.parseInt(reference);
		if(index <= 0 || index > values.length) {
			return null;
		}
		return this.values[index - 1];
	}

	@Override
	public void set(String reference, SILObject value) {
		// TODO so nasty, so wrong
		int index = Integer.parseInt(reference);
		if(index <= 0 || index > values.length) {
			throw new UnsupportedOperationException("Global variable set.");
		}
		this.values[index - 1] = value;
	}
}
