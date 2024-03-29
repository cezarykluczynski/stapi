package com.cezarykluczynski.stapi.util.exception;

public class StapiRuntimeException extends RuntimeException {

	public StapiRuntimeException(String message) {
		super(message);
	}

	public StapiRuntimeException(Throwable cause) {
		super(cause);
	}

	public StapiRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
