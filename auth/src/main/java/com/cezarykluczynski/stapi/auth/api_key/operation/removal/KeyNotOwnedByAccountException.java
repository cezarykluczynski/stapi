package com.cezarykluczynski.stapi.auth.api_key.operation.removal;

class KeyNotOwnedByAccountException extends ApiKeyRemovalException {

	KeyNotOwnedByAccountException() {
		super("Key not owned by account");
	}

}
