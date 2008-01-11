package org.sodeja.sil.runtime.memory;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.sil.runtime.InstanceSpecification;
import org.sodeja.sil.runtime.SILObject;
import org.sodeja.sil.runtime.SILPrimitiveObject;
import org.sodeja.sil.runtime.protocol.ProtocolFactory;

public class ObjectManager {
	
	private int internalRefId = 0;
	private int externalRefId = 0;
	
	public final InternalReference nilRef;
	public final ExternalReference nilExternalRef;
	private final InternalReference symbolClassRef;
	
	private final Map<InternalReference, SILObject> objects;
	private final Map<ExternalReference, Object> externals;
	
	private final Map<String, InternalReference> symbols;
	
	private final ProtocolFactory protocols = ProtocolFactory.getInstance();
	
	public ObjectManager() {
		nilRef = new InternalReference(this, internalRefId++);
		nilExternalRef = new ExternalReference(this, externalRefId++);
		symbolClassRef = null;
		
		objects = new HashMap<InternalReference, SILObject>();
		externals = new HashMap<ExternalReference, Object>();
		
		symbols = new HashMap<String, InternalReference>();
	}

	public InternalReference create(InternalReference typeClass) {
		InternalReference instanceRef = protocols.classProtocol.getInstanceSpec(typeClass.getValue());
		SILPrimitiveObject instanceObj = (SILPrimitiveObject) instanceRef.getValue();

		InstanceSpecification instanceSpec = (InstanceSpecification) instanceObj.getValue().getValue();
		SILObject newObject = instanceSpec.makeInstance(typeClass);
		
		return put(newObject);
	}
	
	private InternalReference put(SILObject obj) {
		InternalReference ref = new InternalReference(this, internalRefId++);
		objects.put(ref, obj);
		return ref;
	}
	
	public SILObject get(InternalReference ref) {
		return validate(objects.get(ref));
	}
	
	public ExternalReference createLink(Object obj) {
		ExternalReference ref = new ExternalReference(this, externalRefId++);
		externals.put(ref, obj);
		return ref;
	}
	
	public Object getLink(ExternalReference ref) {
		return validate(externals.get(ref));
	}
	
	public Reference makeSymbol(String str) {
		InternalReference ref = symbols.get(str);
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
