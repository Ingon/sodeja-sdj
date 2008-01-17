package org.sodeja.silan.instruction;

import java.lang.reflect.Method;

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
		
		try {
			Method method = obj.getClass().getMethod(selector, new Class[] {});
			Object jobj = method.invoke(obj, new Object[] {});
			mc.push(manager.newValueIfNeeded(jobj));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
