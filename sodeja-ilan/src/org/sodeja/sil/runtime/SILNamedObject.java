package org.sodeja.sil.runtime;

import java.util.ArrayList;
import java.util.List;

public class SILNamedObject extends SILObject {
	private final List<MutablePair<String, SILObject>> locals;
	
	public SILNamedObject(SILClass type, List<String> localNames) {
		super(type);
		
		this.locals = new ArrayList<MutablePair<String, SILObject>>();
		for(String symbol : localNames) {
			locals.add(new MutablePair<String, SILObject>(symbol, (SILObject) null));
		}
	}
	
	public SILObject at(String name) {
		return findLocalPair(name).second;
	}

	public void at_put(String name, SILObject value) {
		findLocalPair(name).second = value;
	}
	
	private MutablePair<String, SILObject> findLocalPair(String name) {
		for(MutablePair<String, SILObject> pair : locals) {
			if(pair.first.equals(name)) {
				return pair;
			}
		}
		
		throw new RuntimeException("Local variable " + name + " not found");
	}
	
	private static class MutablePair<F, S> {
		public F first;
		public S second;
		
		public MutablePair(F first, S second) {
			this.first = first;
			this.second = second;
		}
	}
}
