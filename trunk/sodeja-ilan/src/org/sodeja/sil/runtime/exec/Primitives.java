package org.sodeja.sil.runtime.exec;

import java.util.HashMap;
import java.util.Map;

public class Primitives {
	
	private final Map<Integer, PrimitiveMethod> primitives;
	
	public Primitives() {
		primitives = new HashMap<Integer, PrimitiveMethod>();
	}

	public void invoke(Process process, int id) {
		PrimitiveMethod method = primitives.get(id);
		method.call(process);
	}
}
