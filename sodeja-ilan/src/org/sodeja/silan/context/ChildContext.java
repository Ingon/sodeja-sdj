package org.sodeja.silan.context;

public interface ChildContext extends Context {
	public Context getParent();
}
