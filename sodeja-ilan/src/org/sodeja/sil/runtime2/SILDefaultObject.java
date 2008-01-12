package org.sodeja.sil.runtime2;

public class SILDefaultObject implements SILObject {
	
	private SILObject type;
	protected SILObject[] namedVars;
	protected SILObject[] indexVars;
	
	public SILDefaultObject(int namedSize, int indexSize) {
		namedVars = new SILObject[namedSize];
		indexVars = new SILObject[indexSize];
	}
	
	@Override
	public SILObject getType() {
		return type;
	}

	protected void setType(SILObject type) {
		this.type = type;
	}
}
