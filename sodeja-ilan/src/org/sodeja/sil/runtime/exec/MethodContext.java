package org.sodeja.sil.runtime.exec;

public class MethodContext implements Context {
	
	public final Context parentContext;
	public final Method method;
	public final int resultIndex;
	
	private final SILObject[] objects; 
	
	public MethodContext(Context parentContext, Method method, int[] argumentIndexes, int resultIndex) {
		this.parentContext = parentContext;
		this.method = method;
		this.resultIndex = resultIndex;
		
		objects = new SILObject[method.argumentsCount + method.tempCount];
		for(int i = 0;i < argumentIndexes.length;i++) {
			objects[i] = parentContext.get(argumentIndexes[i]);
		}
	}

	@Override
	public SILObject get(int index) {
		return objects[index];
	}

	@Override
	public void set(int index, SILObject obj) {
		objects[index] = obj;
	}
}
