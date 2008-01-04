package org.sodeja.il.runtime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.il.sdk.ILClass;
import org.sodeja.il.sdk.ILClassLambda;
import org.sodeja.il.sdk.ILDefaultObject;
import org.sodeja.il.sdk.ILJavaObject;
import org.sodeja.il.sdk.ILJavaObjectClass;
import org.sodeja.il.sdk.ILObject;
import org.sodeja.il.sdk.ILSymbol;
import org.sodeja.il.sdk.ILSymbolClass;

public class SDK {
	private static final SDK instance = new SDK();
	
	public static SDK getInstance() {
		return instance;
	}
	
	private final ILClass metaType;
	private final ILClass rootType;
	private final ILSymbolClass symbolType;
	
	private final Map<ILSymbol, ILClass> types;
	
	private SDK() {
		metaType = new ILClass(new ILSymbol(null, "ILMetaObject"), null);
		
		rootType = new ILClass(new ILSymbol(null, "ILObject"), null);
		symbolType = new ILSymbolClass(new ILSymbol(null, "ILSymbol"), rootType);
		
		types = new HashMap<ILSymbol, ILClass>();
		types.put(makeSymbol("ILObject"), rootType);
		types.put(makeSymbol("ILSymbol"), symbolType);
		types.put(makeSymbol("ILInteger"), makeIntegerType());

		initMetaType();
	}
	
	private void initMetaType() {
		metaType.defineLambda(makeSymbol("new"), new NewLambda());
	}

	public ILSymbol makeSymbol(String name) {
		return (ILSymbol) symbolType.makeInstance(name);
	}
	
	public ILObject makeInstance(String name, Object value) {
		ILClass type = types.get(makeSymbol(name));
		return type.makeInstance(value);
	}

	private ILClass makeIntegerType() {
		ILJavaObjectClass type = new ILJavaObjectClass(makeSymbol("ILInteger"), rootType);
		type.defineLambda(makeSymbol("+"), getIntegerAddLambda());
		type.defineLambda(makeSymbol("*"), getIntegerMulLambda());
		return type;
	}
	
	private ILClassLambda getIntegerAddLambda() {
		return new ILClassLambda() {
			@Override
			public ILObject applyObject(ILObject value, List<ILObject> values) {
				if(values.size() != 1) {
					throw new RuntimeException();
				}
				Integer primValue = (Integer) ((ILJavaObject) value).getValue();
				Integer secValue = (Integer) ((ILJavaObject) values.get(0)).getValue();
				return makeInstance("ILInteger", primValue + secValue);
			}

			@Override
			public ILClass getType() {
				throw new UnsupportedOperationException();
			}};
	}

	private ILClassLambda getIntegerMulLambda() {
		return new ILClassLambda() {
			@Override
			public ILObject applyObject(ILObject value, List<ILObject> values) {
				if(values.size() != 1) {
					throw new RuntimeException();
				}
				Integer primValue = (Integer) ((ILJavaObject) value).getValue();
				Integer secValue = (Integer) ((ILJavaObject) values.get(0)).getValue();
				return makeInstance("ILInteger", primValue * secValue);
			}

			@Override
			public ILClass getType() {
				throw new UnsupportedOperationException();
			}};
	}

	public ILClass getRootType() {
		return rootType;
	}

	public ILClass getMetaType() {
		return metaType;
	}

	private static class NewLambda implements ILClassLambda {
		@Override
		public ILObject applyObject(ILObject value, List<ILObject> values) {
			if(! (value instanceof ILClass)) {
				throw new RuntimeException("Trying to create something which is not class!");
			}
			
			ILClass type = (ILClass) value;
			ILObject obj = new ILDefaultObject(type);
			
			// Invoke constructor
			if(type.getConstructorLambda() != null) {
				type.getConstructorLambda().applyObject(obj, values);
			}
			
			return obj;
		}

		@Override
		public ILClass getType() {
			throw new UnsupportedOperationException();
		}
	}
}
