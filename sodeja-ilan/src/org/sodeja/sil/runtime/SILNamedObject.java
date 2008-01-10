package org.sodeja.sil.runtime;

import java.util.ArrayList;
import java.util.List;

public class SILNamedObject extends SILObject {
	private final List<MutablePair<Reference, Reference>> locals;
	
	public SILNamedObject(Reference type, List<Reference> localNames) {
		super(type);
		
		this.locals = new ArrayList<MutablePair<Reference, Reference>>();
		for(Reference symbol : localNames) {
			locals.add(new MutablePair<Reference, Reference>(symbol, (Reference) null));
		}
	}
	
	public Reference at(Reference name) {
		return findLocalPair(name).second;
	}

	public void at_put(Reference name, Reference value) {
		findLocalPair(name).second = value;
	}
	
	private MutablePair<Reference, Reference> findLocalPair(Reference name) {
		for(MutablePair<Reference, Reference> pair : locals) {
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
