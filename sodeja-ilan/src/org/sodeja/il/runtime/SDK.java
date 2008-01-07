package org.sodeja.il.runtime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.il.sdk.ILClass;
import org.sodeja.il.sdk.ILClassLambda;
import org.sodeja.il.sdk.ILJavaClass;
import org.sodeja.il.sdk.ILJavaObject;
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
		metaType = new ILClass(new ILSymbol("ILMetaObject"), null);
		
		rootType = new ILClass(new ILSymbol("ILObject"), null);
		symbolType = new ILSymbolClass();
		
		types = new HashMap<ILSymbol, ILClass>();
		types.put(new ILSymbol("ILObject"), rootType);
		types.put(new ILSymbol("ILSymbol"), symbolType);
		
		makeIntegerType();
		registerJavaClass("java.lang.Boolean");

		initMetaType();
	}
	
	private void initMetaType() {
		metaType.defineLambda(new ILSymbol("new"), new NewLambda());
	}

	public ILObject makeInstance(Object value) {
		Class clazz = value.getClass();
		ILJavaClass type = (ILJavaClass) types.get(new ILSymbol(clazz.getName()));
		if(type == null) {
			type = registerJavaClass(clazz);
		}
		
		return new ILJavaObject(type, value);
	}

	private ILClass makeIntegerType() {
		registerJavaClass("java.lang.Integer");
		ILClass type = getJavaClass("java.lang.Integer");
		type.defineLambda(new ILSymbol("+"), getIntegerAddLambda());
		type.defineLambda(new ILSymbol("*"), getIntegerMulLambda());
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
				return makeInstance(primValue + secValue);
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
				return makeInstance(primValue * secValue);
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

	public ILClass getSymbolType() {
		return symbolType;
	}
	
	private static class NewLambda implements ILClassLambda {
		@Override
		public ILObject applyObject(ILObject value, List<ILObject> values) {
			if(! (value instanceof ILClass)) {
				throw new RuntimeException("Trying to create something which is not class!");
			}
			
			ILClass type = (ILClass) value;
			return type.newInstance(values);
		}

		@Override
		public ILClass getType() {
			throw new UnsupportedOperationException();
		}
	}

	public ILJavaClass registerJavaClass(String value) {
		return registerJavaClass(ReflectUtils.resolveClass(value));
	}
	
	private ILJavaClass registerJavaClass(Class clazz) {
		List<Class> hierarhy = ReflectUtils.loadHierarchy(clazz);
		List<ILSymbol> names = ListUtils.map(hierarhy, new Function1<ILSymbol, Class>() {
			@Override
			public ILSymbol execute(Class p) {
				return new ILSymbol(p.getName());
			}});
		
		ILJavaClass symbolClass = null;
		for(int i = 1, n = hierarhy.size();i < n;i++) {
			Class tempClass = hierarhy.get(i);
			ILSymbol symbol = names.get(i);
			
			symbolClass = (ILJavaClass) types.get(symbol);
			if(symbolClass != null) {
				continue;
			}
			
			symbolClass = new ILJavaClass(symbol, types.get(names.get(i - 1)), tempClass);
			types.put(symbol, symbolClass);
		}
		return symbolClass;
	}

	public ILClass getJavaClass(String val) {
		return types.get(new ILSymbol(val));
	}
}
