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

	@Override
	public SILObject resolve(String reference) {
		if(reference.equals("self")) {
			return ((MethodContext) home).receiver;
		} else if(reference.equals("super")) {
			throw new UnsupportedOperationException();
		} else if(reference.equals("thisContext")) {
			throw new UnsupportedOperationException();
		}
		
		SILObject val = argumentValues.get(reference);
		if(val != null) {
			return val;
		}
		
		val = super.resolve(reference);
		if(val != null) {
			return val;
		}
		
		val = process.vm.objects.getGlobal(reference);
		if(val != null) {
			return val;
		}
		
		throw new UnsupportedOperationException("Not implemented above this scope - " + reference);
	}
	
	public Context getHome() {
		return home;
	}
}
