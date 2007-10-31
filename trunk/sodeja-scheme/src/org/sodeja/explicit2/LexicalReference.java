package org.sodeja.explicit2;

class LexicalReference implements Reference {
	protected final int index;
	
	public LexicalReference(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "L" + index;
	}
}
