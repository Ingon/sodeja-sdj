package org.sodeja.scheme2.execute.procedure;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.scheme2.execute.Library;
import org.sodeja.scheme2.execute.procedure.pair.CarProcedure;
import org.sodeja.scheme2.execute.procedure.pair.CdrProcedure;
import org.sodeja.scheme2.execute.procedure.pair.ConsProcedure;

public class PairLibrary implements Library {
	@Override
	public Map<String, Object> contents() {
		return new HashMap<String, Object>() {
			private static final long serialVersionUID = 4309646054015722531L;
		{
			put("cons", new ConsProcedure());
			put("car", new CarProcedure());
			put("cdr", new CdrProcedure());
		}};
	}
}
