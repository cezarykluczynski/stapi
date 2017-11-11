package com.cezarykluczynski.stapi.auth.api_key.operation.common;

public class KeyDoesNotExistException extends ApiKeyException {

	public KeyDoesNotExistException() {
		super("API key does not exist");
	}

}
