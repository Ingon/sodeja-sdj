package org.sodeja.sil.sdk;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.sil.runtime.SILObject;
import org.sodeja.sil.runtime.context.Context;

public class PrimitiveRepository {
	private final Map<Integer, PrimitiveFunction> primitives;
	
	public PrimitiveRepository() {
		primitives = new HashMap<Integer, PrimitiveFunction>();
	}
	
	public SILObject execute(int id, Context ctx) {
		PrimitiveFunction fun = primitives.get(id);
		if(fun == null) {
			throw new PrimitiveExecutionException("Function with id " + id + " not found!");
		}
		return fun.perform(ctx);
	}
}
