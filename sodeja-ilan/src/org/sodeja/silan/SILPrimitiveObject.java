package org.sodeja.silan;

public class SILPrimitiveObject<T> implements SILObject {

	private final TypeSupplier typeSupplier;
	private final String typeName;
	
	public final T value;
	
	public SILPrimitiveObject(TypeSupplier typeSupplier, String typeName, T value) {
		this.typeSupplier = typeSupplier;
		this.typeName = typeName;
		
		this.value = value;
	}

	@Override
	public SILClass getType() {
		return typeSupplier.getByTypeName(typeName);
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
		return null;
	}
}
