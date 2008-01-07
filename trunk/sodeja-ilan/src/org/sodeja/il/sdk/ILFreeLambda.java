package org.sodeja.il.sdk;

import java.util.List;

public interface ILFreeLambda extends ILLambda {
	ILObject apply(List<ILObject> values);
	int getArgumentsCount();
}
