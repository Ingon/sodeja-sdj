package org.sodeja.sil.runtime;

public class References {
	public static final Reference NIL = new Reference();

	public static final Reference JAVA_INTEGER_CLASS = new Reference();
	public static final Reference JAVA_STRING_CLASS = new Reference();
	
	public static final Reference SIL_METHOD_LITERALS = new Reference();
	public static final Reference SIL_METHOD_BODY = new Reference();
	
	public static final Reference SIL_CLASS_SUPERCLASS = new Reference();
	public static final Reference SIL_CLASS_MESSAGE_DICT = new Reference();
	public static final Reference SIL_CLASS_INSTANCE_SPEC = new Reference();
	
	public static final Reference SIL_METHOD_CTX_CLASS = new Reference();
	public static final Reference SIL_METHOD_CTX_SENDER = new Reference();
	public static final Reference SIL_METHOD_CTX_INSTRUCTION_POINTER = new Reference();
	public static final Reference SIL_METHOD_CTX_STACK_POINTER = new Reference();
	public static final Reference SIL_METHOD_CTX_METHOD = new Reference();
	public static final Reference SIL_METHOD_CTX_RECEIVER = new Reference();
	public static final Reference SIL_METHOD_CTX_ARGUMENTS = new Reference();
	public static final Reference SIL_METHOD_CTX_TEMPORARIES = new Reference();
	public static final Reference SIL_METHOD_CTX_STACK = new Reference();

	public static final Reference SIL_BLOCK_CTX_CLASS = new Reference();
	public static final Reference SIL_BLOCK_CTX_CALLER = new Reference();
	public static final Reference SIL_BLOCK_CTX_INSTRUCTION_POINTER = new Reference();
	public static final Reference SIL_BLOCK_CTX_STACK_POINTER = new Reference();
	public static final Reference SIL_BLOCK_CTX_ARGUMENT_COUNT = new Reference();
	public static final Reference SIL_BLOCK_CTX_INITIAL_INSTRUCTION_POINTER = new Reference();
	public static final Reference SIL_BLOCK_CTX_HOME = new Reference();
	public static final Reference SIL_BLOCK_CTX_STACK = new Reference();
}
