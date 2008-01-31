package org.sodeja.silan.instruction;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.sodeja.silan.Process;
import org.sodeja.silan.SILObject;
import org.sodeja.silan.context.MethodContext;
import org.sodeja.silan.objects.ImageObjectManager;

public class InvokePrimitiveMethodInstruction extends PrimitiveInstruction {
	private final String selector;	
	
	public InvokePrimitiveMethodInstruction(ImageObjectManager manager, String selector) {
		super(manager);
		this.selector = selector;
	}

	@Override
	public void execute(Process process) {
		MethodContext mc = (MethodContext) process.getActiveContext();
		SILObject obj = mc.getReceiver();
		
		List<Method> methods = find(obj, selector);
		if(methods.size() != 1) {
			throw new UnsupportedOperationException();
		}
		
		Method method = methods.get(0);
		int paramsCount = method.getParameterTypes().length;
		Object[] params = new Object[paramsCount];
		for(int i = params.length - 1; i >= 0; i--) {
			params[i] = manager.unwrap(mc.pop());
		}
		
		try {
			Object result = method.invoke(obj, params);
			mc.push(manager.wrap(result));
		} catch(Exception exc) {
			throw new RuntimeException(exc);
		}
	}
	
	private List<Method> find(SILObject obj, String selector) {
		Method[] methods = obj.getClass().getMethods();
		List<Method> result = new ArrayList<Method>();
		for(Method method : methods) {
			if(method.getName().equals(selector)) {
				result.add(method);
			}
		}
		return result;
	}
}
