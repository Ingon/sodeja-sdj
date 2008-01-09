package org.sodeja.sil.runtime;

import java.util.Map;


public class ObjectTable {
	public final Map<Reference, SILObject> objectMap;

	public ObjectTable(Map<Reference, SILObject> objectMap) {
		this.objectMap = objectMap;
	}
}
