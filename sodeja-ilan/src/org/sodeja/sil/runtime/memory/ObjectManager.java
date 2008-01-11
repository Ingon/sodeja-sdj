package org.sodeja.sil.runtime.memory;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.sil.runtime.InstanceSpecification;
import org.sodeja.sil.runtime.SILObject;
import org.sodeja.sil.runtime.SILPrimitiveObject;
import org.sodeja.sil.runtime.SILProtocolFactory;

public class ObjectManager {
	
	private int internalRefId = 0;
	private int externalRefId = 0;
	
	private final SILInternalReference symbolClassRef;
	
	private final Map<SILInternalReference, SILObject> objects;
	private final Map<SILExternalReference, Object> externals;
	
	private final Map<String, SILInternalReference> symbols;
	
	private final SILProtocolFactory protocols = SILProtocolFactory.getInstance();
	
	public ObjectManager() {
		symbolClassRef = null;
		
		objects = new HashMap<SILInternalReference, SILObject>();
		externals = new HashMap<SILExternalReference, Object>();
		
		symbols = new HashMap<String, SILInternalReference>();
	}

	public SILInternalReference create(SILInternalReference typeClass) {
		SILObject type = get(typeClass);
		
		SILInternalReference instanceRef = protocols.classProtocol.getInstanceSpec(type);
		SILPrimitiveObject instanceObj = (SILPrimitiveObject) get(instanceRef);
		
		InstanceSpecification instanceSpec = (InstanceSpecification) getLink(instanceObj.getValue());
		SILObject newObject = instanceSpec.makeInstance(typeClass);
		
		return put(newObject);
	}
	
	private SILInternalReference put(SILObject obj) {
		SILInternalReference ref = new SILInternalReference(internalRefId++);
		objects.put(ref, obj);
		return ref;
	}
	
	public SILObject get(SILInternalReference ref) {
		return validate(objects.get(ref));
	}
	
	public SILExternalReference createLink(Object obj) {
		SILExternalReference ref = new SILExternalReference(externalRefId++);
		externals.put(ref, obj);
		return ref;
	}
	
	public Object getLink(SILExternalReference ref) {
		return validate(externals.get(ref));
	}
	
	public SILReference makeSymbol(String str) {
		SILInternalReference ref = symbols.get(str);
		if(ref != null) {
			return ref;
		}
		
		ref = create(symbolClassRef);
		symbols.put(str, ref);
		
		SILPrimitiveObject symbolObj = (SILPrimitiveObject) get(ref);
		symbolObj.setValue(createLink(str));
		
		return ref;
	}

	private static <T> T validate(T obj) {
		if(obj == null) {
			throw new RuntimeException("Object not found");
		}
		return obj;
	}
}
