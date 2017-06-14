package com.cezarykluczynski.stapi.util.exception;

public class StapiRuntimeException extends RuntimeException {

	public StapiRuntimeException(String message) {
		super(message);
	}

	public StapiRuntimeException(Throwable cause) {
		super(cause);
	}
}
