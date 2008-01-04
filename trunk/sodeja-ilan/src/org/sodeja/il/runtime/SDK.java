package org.sodeja.il.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.il.sdk.ILClass;
import org.sodeja.il.sdk.ILClassLambda;
import org.sodeja.il.sdk.ILDefaultObject;
import org.sodeja.il.sdk.ILJavaObject;
import org.sodeja.il.sdk.ILJavaClass;
import org.sodeja.il.sdk.ILObject;
import org.sodeja.il.sdk.ILSymbol;
import org.sodeja.il.sdk.ILSymbolClass;
import org.sodeja.lang.reflect.ReflectUtils;

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
		
		makeIntegerType();

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
		registerJavaClass("java.lang.Integer");
		ILClass type = getJavaClass("java.lang.Integer");
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
				Integer primValue = ((ILJavaObject<Integer>) value).getValue();
				Integer secValue = ((ILJavaObject<Integer>) values.get(0)).getValue();
				return makeInstance("java.lang.Integer", primValue + secValue);
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
				Integer primValue = ((ILJavaObject<Integer>) value).getValue();
				Integer secValue = ((ILJavaObject<Integer>) values.get(0)).getValue();
				return makeInstance("java.lang.Integer", primValue * secValue);
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

	public void registerJavaClass(String value) {
		Class clazz = ReflectUtils.resolveClass(value);
		List<Class> hierarhy = ReflectUtils.loadHierarchy(clazz);
		List<ILSymbol> names = ListUtils.map(hierarhy, new Function1<ILSymbol, Class>() {
			@Override
			public ILSymbol execute(Class p) {
				return makeSymbol(p.getName());
			}});
		
		for(int i = 1, n = hierarhy.size();i < n;i++) {
			Class tempClass = hierarhy.get(i);
			ILSymbol symbol = names.get(i);
			
			ILClass symbolClass = types.get(symbol);
			if(symbolClass != null) {
				continue;
			}
			
			symbolClass = new ILJavaClass(symbol, types.get(names.get(i - 1)));
			types.put(symbol, symbolClass);
		}
	}

	public ILClass getJavaClass(String val) {
		return types.get(makeSymbol(val));
	}
}
