package org.sodeja.explicit2;

class ParentLexicalReference implements Reference {
	protected final Reference parent;
	
	public ParentLexicalReference(Reference parent) {
		this.parent = parent;
	}

	@Override
	public Object resolveValue(DynamicEnviroment dynamic, LexicalEnviroment lexical) {
		return parent.resolveValue(dynamic, lexical.getParent());
	}

	@Override
	public String toString() {
		return "PR:" + parent.toString();
	}
}
