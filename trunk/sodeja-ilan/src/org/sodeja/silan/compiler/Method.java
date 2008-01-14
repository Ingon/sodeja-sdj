package org.sodeja.silan.compiler;

public class Method {
	public final Header header;
	public final ExecutableCode code;
	
	public Method(Header header, ExecutableCode code) {
		this.header = header;
		this.code = code;
	}
}
