package org.sodeja.scheme.execute.form;

import java.util.List;

import org.sodeja.scheme.execute.Executable;
import org.sodeja.scheme.execute.Frame;
import org.sodeja.scheme.parse.model.Expression;

public interface Form extends Executable {
	public Object execute(Frame frame, List<Expression> expressions);
}
