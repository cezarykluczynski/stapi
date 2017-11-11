package com.cezarykluczynski.stapi.auth.api_key.operation.common;

public class KeyNotOwnedByAccountException extends ApiKeyException {

	public KeyNotOwnedByAccountException() {
		super("Key not owned by account");
	}

}
