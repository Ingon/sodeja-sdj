package org.sodeja.silan.context;

import org.sodeja.silan.CompiledBlock;
import org.sodeja.silan.SILObject;

public class BlockContext extends AbstractChildContext {
	
	private final Context home;
	
	public BlockContext(Context parent, Context home, 
			CompiledBlock block, SILObject[] arguments) {
		super(parent, block, arguments);
		
		this.home = home;
	}

	public Context getHome() {
		return home;
	}
}
