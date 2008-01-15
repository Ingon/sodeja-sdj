package org.sodeja.silan;

public class SILPrimitiveObject<T> implements SILObject {

	private final ObjectManager manager;
	private final String typeName;
	
	public final T value;
	
	public SILPrimitiveObject(ObjectManager manager, String typeName, T value) {
		this.manager = manager;
		this.typeName = typeName;
		
		this.value = value;
	}

	@Override
	public SILClass getType() {
		return manager.getByTypeName(typeName);
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public void set(String reference, SILObject value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SILObject get(String reference) {
		throw new UnsupportedOperationException();
	}
}
