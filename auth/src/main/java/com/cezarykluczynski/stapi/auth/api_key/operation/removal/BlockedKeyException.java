package com.cezarykluczynski.stapi.auth.api_key.operation.removal;

import com.cezarykluczynski.stapi.auth.api_key.operation.common.ApiKeyException;

class BlockedKeyException extends ApiKeyException {

	BlockedKeyException() {
		super("API key is blocked and cannot be deleted");
	}

}
