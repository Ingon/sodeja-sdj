package org.sodeja.sil.runtime;

public class SILCompiledMethod extends SILDefaultObject {
	protected SILCompiledMethod() {
		super(4, 0);
	}

	public SILObject getMethodClass() {
		return namedVars[0];
	}

	protected void setMethodClass(SILObject methodClass) {
		namedVars[0] = methodClass;
	}

	public SILObject getSelector() {
		return namedVars[1];
	}

	protected void setSelector(SILObject selector) {
		namedVars[1] = selector;
	}

	public SILObject getSource() {
		return namedVars[2];
	}

	protected void setSource(SILObject source) {
		namedVars[2] = source;
	}

	public SILObject getCode() {
		return namedVars[3];
	}

	protected void setCode(SILObject code) {
		namedVars[3] = code;
	}
}
