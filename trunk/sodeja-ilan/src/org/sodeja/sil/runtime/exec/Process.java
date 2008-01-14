package org.sodeja.sil.runtime.exec;

public class Process {
	private Context activeContext;

	public Context getActiveContext() {
		return activeContext;
	}

	public void setActiveContext(Context activeContext) {
		this.activeContext = activeContext;
	}
}
