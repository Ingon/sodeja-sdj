package org.sodeja.silan.compiler.src;

public class MethodDeclaration {
	public final MethodHeader header;
	public final ExecutableCode code;
	
	public MethodDeclaration(MethodHeader header, ExecutableCode code) {
		this.header = header;
		this.code = code;
	}
}
