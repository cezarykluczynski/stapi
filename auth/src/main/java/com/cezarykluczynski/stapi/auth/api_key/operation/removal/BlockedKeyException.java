package com.cezarykluczynski.stapi.auth.api_key.operation.removal;

class BlockedKeyException extends ApiKeyRemovalException {

	BlockedKeyException() {
		super("API key is blocked and cannot be deleted");
	}

}
