package org.sodeja.sil.sdk;

public class PrimitiveExecutionException extends RuntimeException {
	private static final long serialVersionUID = 8232842738387734380L;

	public PrimitiveExecutionException(String message, Throwable cause) {
		super(message, cause);
	}

	public PrimitiveExecutionException(String message) {
		super(message);
	}
}
