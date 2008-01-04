package org.sodeja.il.runtime;

import java.util.List;

public interface ILFreeLambda extends ILLambda {
	ILObject apply(List<ILObject> values);
}
