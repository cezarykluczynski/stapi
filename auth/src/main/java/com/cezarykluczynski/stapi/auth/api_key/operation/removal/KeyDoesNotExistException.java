package com.cezarykluczynski.stapi.auth.api_key.operation.removal;

class KeyDoesNotExistException extends ApiKeyRemovalException {

	KeyDoesNotExistException() {
		super("API key does not exist");
	}

}
