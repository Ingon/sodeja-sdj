package org.sodeja.il.sdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.functional.Predicate1;
import org.sodeja.il.runtime.SDK;
import org.sodeja.lang.reflect.ReflectUtils;

public class ILJavaClass extends ILClass {
	
	private final Class clazz;
	
	public ILJavaClass(ILSymbol name, ILClass parent, Class clazz) {
		super(name, parent);
		this.clazz = clazz;
	}

	@Override
	public ILObject newInstance(List<ILObject> values) {
		if(values.size() != 0) {
			throw new UnsupportedOperationException();
		}
		return new ILJavaObject(this, ReflectUtils.newInstance(clazz));
	}
	
	@Override
	public ILClassLambda getLambda(final ILSymbol symbol) {
		ILClassLambda lambda = super.getLambda(symbol);
		if(! (lambda instanceof ILNotFoundLambda || lambda instanceof ILPrimitiveClassLambda)) {
			return lambda;
		}
		
		List<Method> methods = ListUtils.asList(clazz.getDeclaredMethods());
		List<Method> applicableMethods = ListUtils.filter(methods, new Predicate1<Method>() {
			@Override
			public Boolean execute(Method p) {
				int modifiers = p.getModifiers();
				if(!Modifier.isPublic(modifiers) || Modifier.isStatic(modifiers)) {
					return false;
				}
				return p.getName().equals(symbol.getValue());
			}});
		
		if(applicableMethods.isEmpty()) {
			return lambda;
		}
		
		if(lambda instanceof ILPrimitiveClassLambda) {
			methods.addAll(((ILPrimitiveClassLambda) lambda).methods);
		}
		
		return new ILPrimitiveClassLambda(applicableMethods);
	}

	private static class ILPrimitiveClassLambda implements ILClassLambda {
		public final List<Method> methods;
		
		public ILPrimitiveClassLambda(List<Method> methods) {
			this.methods = methods;
		}
		
		@Override
		public ILObject applyObject(ILObject value, final List<ILObject> values) {
			// TODO makes only size based search
			List<Method> candidates = ListUtils.filter(methods, new Predicate1<Method>() {
				@Override
				public Boolean execute(Method p) {
					return p.getParameterTypes().length == values.size();
				}});
			
			if(candidates.size() != 1) {
				throw new UnsupportedOperationException();
			}
			
			final Object obj = ((ILJavaObject) value).value;
			
			final Method realMethod = candidates.get(0);
			List<Object> vals = null;
			if(realMethod.getParameterTypes().length > 1) {
				vals = defaultMap(values);
			} else {
				Class paramType = realMethod.getParameterTypes()[0];
				if(! paramType.isInterface()) {
					vals = defaultMap(values);
				} else {
					vals = new ArrayList<Object>();
					Object localObj = Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {paramType}, new InvocationHandler() {
						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							return null;
						}});
					vals.add(localObj);
				}
			}
				
			return execute(obj, realMethod, vals);
		}

		private ILObject execute(Object obj, Method method, List<Object> vals) {
			// TODO should perform search for best match
			Object result = ReflectUtils.executeMethod(obj, method, vals.toArray(new Object[vals.size()]));
			if(result == null) {
				return null;
			}
			return SDK.getInstance().makeInstance(result);
		}
		
		private List<Object> defaultMap(List<ILObject> values) {
			return ListUtils.map(values, new Function1<Object, ILObject>() {
				@Override
				public Object execute(ILObject p) {
					return ((ILJavaObject) p).value;
				}});
		}
		
		@Override
		public ILClass getType() {
			throw new UnsupportedOperationException();
		}
	}
}
