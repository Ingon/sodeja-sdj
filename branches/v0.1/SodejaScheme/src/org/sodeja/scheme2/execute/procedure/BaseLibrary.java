package org.sodeja.scheme2.execute.procedure;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.runtime.procedure.logical.EqProcedure;
import org.sodeja.scheme2.execute.Library;

public class BaseLibrary implements Library {
	@Override
	public Map<String, Object> contents() {
		return new HashMap<String, Object>() {
			private static final long serialVersionUID = 4309646054015722531L;
		{
			put("eq?", new EqProcedure());
		}};
	}
}
