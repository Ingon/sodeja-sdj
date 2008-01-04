package org.sodeja.il.runtime;

import java.util.List;

public interface ILClassLambda extends ILLambda {
	ILObject applyObject(ILObject value, List<ILObject> values);
}
