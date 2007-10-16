package org.sodeja.scheme.execute.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.scheme.execute.Frame;
import org.sodeja.scheme.execute.Procedure;
import org.sodeja.scheme.parse.model.Expression;

public class LispProcedure implements Procedure {
	
	private final Frame frame;
	private final List<String> params;
	private final List<Expression> parts;
	
	public LispProcedure(Frame frame, List<String> params, List<Expression> parts) {
		this.frame = frame;
		this.params = params;
		this.parts = parts;
	}

	@Override
	public Object execute(final Object... vals) {
		Map<String, Object> paramsMapping = new HashMap<String, Object>() {
			private static final long serialVersionUID = 7740081972870762415L;
		{
			for(int i = 0, n = params.size();i < n;i++) {
				put(params.get(i), vals[i]);
			}
		}};
		Frame thisFrame = new Frame(frame, paramsMapping);
		
		Object result = null;
		for(Expression part : parts) {
			result = thisFrame.eval(part);
		}
		
//		System.out.print("AAA: " + paramsMapping);
//		System.out.print(" TTT: " + parts);
//		System.out.println(" RRR: " + result);
		
		return result;
	}
}
