package org.sodeja.il.runtime;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.il.sdk.ILClass;
import org.sodeja.il.sdk.ILFreeLambda;
import org.sodeja.il.sdk.ILJavaClass;
import org.sodeja.il.sdk.ILJavaObject;
import org.sodeja.il.sdk.ILLambda;
import org.sodeja.il.sdk.ILObject;
import org.sodeja.il.sdk.ILSymbol;

public class RootContext extends AbstractContext {
	
	public RootContext() {
		define(new ILSymbol("print"), new ILFreeLambda() {
			@Override
			public ILObject apply(List<ILObject> values) {
				for(ILObject val : values) {
					System.out.println(val);
				}
				return null;
			}

			@Override
			public ILClass getType() {
				throw new UnsupportedOperationException();
			}});
		define(new ILSymbol("promoteTo"), new ILFreeLambda() {
			@Override
			public ILObject apply(List<ILObject> values) {
				ILJavaClass clazz = (ILJavaClass) values.get(0);
				final ILFreeLambda lambda = (ILFreeLambda) values.get(1);
				
				Object localObj = Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {clazz.getClazz()}, new InvocationHandler() {
				@Override
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					List<Object> values = ListUtils.asList(args);
					List<ILObject> promotedValues = ListUtils.map(values, new Function1<ILObject, Object>() {
						@Override
						public ILObject execute(Object p) {
							return SDK.getInstance().makeInstance(p);
						}});
					lambda.apply(promotedValues);
					return null;
				}});
				
				return SDK.getInstance().makeInstance(localObj);
			}

			@Override
			public ILClass getType() {
				throw new UnsupportedOperationException();
			}});
//		define(new ILSymbol("makeAsDelegate"), new ILFreeLambda() {
//			@Override
//			public ILObject apply(List<ILObject> values) {
//				ILJavaClass ilClazz = (ILJavaClass) values.get(0);
//				ILJavaObject<String> val = (ILJavaObject<String>) values.get(1);
//				
//				
//			}
//
//			@Override
//			public ILClass getType() {
//				throw new UnsupportedOperationException();
//			}});
	}
	
	@Override
	public void define(ILSymbol name, ILObject value) {
		ILObject tvalue = super.get(name);
		if(tvalue != null) {
			throw new RuntimeException("Redefine is not possible");
		}
		super.define(name, value);
	}

	@Override
	public ILObject get(ILSymbol name) {
		ILObject temp = super.get(name);
		if(temp == null) {
			throw new RuntimeException("Not defined: " + name);
		}
		return temp;
	}
}
